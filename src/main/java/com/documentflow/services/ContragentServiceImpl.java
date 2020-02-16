package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.repositories.ContragentRepository;
import com.documentflow.repositories.specifications.ContragentSpecifications;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContragentServiceImpl implements ContragentService {

    private static final String PERSON = "person";
    private static final String COMPANY = "company";

    @Autowired
    private ContragentRepository contragentRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PersonService personService;

    @Override
    public List<Contragent> searchContragents(@NonNull String searchName) {
        Specification<Contragent> spec = Specification.where(null);
        spec = spec.and(ContragentSpecifications.nameCompanyLike(searchName));
        return contragentRepository.findAll(spec);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        System.out.println(ObjectUtils.isEmpty(list));
    }

    @Override
    public List<Contragent> save(@NonNull ContragentDto contragentDto) {

        final List<Contragent> contragents = new ArrayList<>();

        switch (contragentDto.getTypePerson()) {
            case PERSON:
                Person person = personService.save(contragentDto.getParameters());
                List<Address> addressesPerson = addressService.save(contragentDto.getAddress());
                if (ObjectUtils.isEmpty(addressesPerson)) {
                    throw new RuntimeException("Не указан адрес");
                }
                String searchStringPerson = person.getFirstName() + person.getMiddleName() + person.getLastName();
                List<Contragent> contragentsPerson = addressesPerson.stream()
                        .map(address -> {
                            Contragent contragent = new Contragent(searchStringPerson);
                            contragent.setAddress(address);
                            contragent.setPerson(person);
                            person.getContragents().add(contragent);
                            address.getContragents().add(contragent);
                            return contragent;
                        })
                        .collect(Collectors.toList());

                contragents.addAll(contragentRepository.saveAll(contragentsPerson));
                break;
            case COMPANY:
                Organization organization = organizationService.save(contragentDto.getParameters());
                List<Address> addressesOrganization = addressService.save(contragentDto.getAddress());
                Map<Person, String> employees = personService.save(contragentDto.getEmployee());
                List<Contragent> contragentsOrganization = new ArrayList<>();
                if (ObjectUtils.isEmpty(addressesOrganization)) {
                    throw new RuntimeException("Не указан адрес");
                } else if (!ObjectUtils.isEmpty(employees)) {
                    addressesOrganization.forEach(
                            address -> {
                                employees.forEach(
                                        (employee, position) -> {
                                            String searchStringOrganization = employee.getFirstName() + employee.getMiddleName() +
                                                    employee.getLastName() + position + organization.getName();
                                            Contragent contragent = new Contragent(searchStringOrganization, position);
                                            contragent.setAddress(address);
                                            contragent.setPerson(employee);
                                            contragent.setOrganization(organization);
                                            address.getContragents().add(contragent);
                                            employee.getContragents().add(contragent);
                                            contragentsOrganization.add(contragent);
                                        }
                                );
                            });
                } else {
                    addressesOrganization.forEach(
                            address -> {
                                Contragent contragent = new Contragent(organization.getName());
                                contragent.setAddress(address);
                                contragent.setOrganization(organization);
                                address.getContragents().add(contragent);
                                contragentsOrganization.add(contragent);
                            });
                }
                contragents.addAll(contragentRepository.saveAll(contragentsOrganization));
                break;
            default:
        }
        return contragents;
    }

    @Override
    public Contragent updateEmployee(Long id, String firstName, String middleName, String lastName, String position) {

        Contragent contragent = contragentRepository.findById(id).get();

        firstName = firstName.toUpperCase();
        middleName = middleName.toUpperCase();
        lastName = lastName.toUpperCase();
        position = position.toUpperCase();

        contragent.setPersonPosition(position);
        contragent.setSearchName(firstName + middleName + lastName + position + contragent.getOrganization().getName());
        Person person = contragent.getPerson();
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        contragent.setPerson(person);
        return contragentRepository.save(contragent);
    }
}
