package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import com.documentflow.exceptions.NotFoundIdException;
import com.documentflow.exceptions.NotFoundOrganizationException;
import com.documentflow.repositories.OrganizationRepository;
import com.documentflow.repositories.specifications.OrganizationSpecifications;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ContragentService contragentService;

    @Override
    public Organization save(@NonNull ContragentDtoParameters contragentDto) {

        String nameCompany = contragentDto.getNameCompany();
        Organization organization = new Organization(nameCompany);
        return organizationRepository.save(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Organization> findAll(String nameCompany) {
        Specification<Organization> spec = Specification.where(null);
        if (StringUtils.isNotEmpty(nameCompany)) {
            spec = spec.and(OrganizationSpecifications.nameCompanyLike(nameCompany));
        }
        return organizationRepository.findAll(spec).stream()
                .filter(organization -> {
                    return organization.getContragents().stream()
                            .anyMatch(contragent -> !contragent.getIsDeleted());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Organization find(Long id) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(id);
        if (!optionalOrganization.isPresent()) {
            throw new NotFoundOrganizationException();
        }
        return optionalOrganization.get();
    }

    @Override
    public Organization update(Organization org) {

        if (org.getId() == null) {
            throw new NotFoundIdException();
        }

        Optional<Organization> optionalOrganization = organizationRepository.findById(org.getId());

        if (!optionalOrganization.isPresent()) {
            throw new NotFoundOrganizationException();
        }

        Organization organization = optionalOrganization.get();

        String oldNameOrganization = organization.getName().toUpperCase().replace(" ", "");
        String newNameOrganization = org.getName().toUpperCase().replace(" ", "");

        organization.setName(org.getName());

        organization.getContragents().forEach(contragent -> {
            String oldSearchName = contragent.getSearchName();
            String newSearchName = oldSearchName.replace(oldNameOrganization, newNameOrganization);
            contragent.setSearchName(newSearchName);
            contragentService.save(contragent);
        });

        return organizationRepository.save(organization);
    }

    @Override
    public void delete(Long id) {

        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (!organizationOptional.isPresent()) {
            throw new NotFoundOrganizationException();
        }
        Organization organization = organizationOptional.get();

        organization.getContragents().forEach(contragent -> {
            contragent.setIsDeleted(true);
            contragentService.save(contragent);
        });
    }

    @Override
    public List<Address> getAddresses(Long id) {

        Optional<Organization> optionalOrganization = organizationRepository.findById(id);
        if (!optionalOrganization.isPresent()) {
            throw new NotFoundOrganizationException();
        }

        Organization organization = optionalOrganization.get();

        //берем список записей, которые не помечены как удаленные
        List<Contragent> contragents = organization.getContragents().stream()
                .filter(contragent -> !contragent.getIsDeleted() && contragent.getAddress() != null)
                .collect(Collectors.toList());
        return contragents.stream()
                //меняем ID адреса на ID контрагента, чтобы на фронте можно было удалить запись
                .map(contragent -> new Address(contragent.getId(),
                        contragent.getAddress().getIndex(),
                        contragent.getAddress().getCountry(),
                        contragent.getAddress().getCity(),
                        contragent.getAddress().getStreet(),
                        contragent.getAddress().getHouseNumber(),
                        contragent.getAddress().getApartmentNumber()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContragentDtoEmployee> getEmployees(Long id) {

        Optional<Organization> optionalOrganization = organizationRepository.findById(id);
        if (!optionalOrganization.isPresent()) {
            throw new NotFoundOrganizationException();
        }

        Organization organization = optionalOrganization.get();

        //берем список записей, которые не помечены как удаленные и в которых присутствует сущность персоны
        List<Contragent> contragents = organization.getContragents().stream()
                .filter(contragent -> !contragent.getIsDeleted())
                .filter(contragent -> contragent.getPerson() != null)
                .collect(Collectors.toList());
        return contragents.stream()
                .map(contragent -> new ContragentDtoEmployee(contragent.getId().toString(),
                        contragent.getPerson().getFirstName(),
                        contragent.getPerson().getMiddleName(),
                        contragent.getPerson().getLastName(),
                        contragent.getPersonPosition())
                )
                .collect(Collectors.toList());
    }
}
