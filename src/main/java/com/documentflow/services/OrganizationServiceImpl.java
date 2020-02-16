package com.documentflow.services;

import com.documentflow.entities.Organization;
import com.documentflow.entities.dto.ContragentDtoParameters;
import com.documentflow.repositories.OrganizationRepository;
import com.documentflow.repositories.specifications.OrganizationSpecifications;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Organization save(@NonNull ContragentDtoParameters contragentDto) {

        String nameCompany = contragentDto.getNameCompany();
        Organization organization = new Organization(nameCompany);
        return organizationRepository.save(organization);
    }

    @Override
    public List<Organization> findAll(String nameCompany) {
        Specification<Organization> spec = Specification.where(null);
        if (StringUtils.isNotEmpty(nameCompany)) {
            spec = spec.and(OrganizationSpecifications.nameCompanyLike(nameCompany));
        }
        return organizationRepository.findAll(spec);
    }

    @Override
    public Organization update(Long id, String nameCompany) {
        return organizationRepository.save(new Organization(id, nameCompany));
    }
}
