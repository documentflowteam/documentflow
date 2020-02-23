package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.entities.dto.ContragentDtoBindAddressAndEmployee;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.repositories.ContragentRepository;
import com.documentflow.repositories.specifications.ContragentSpecifications;
import com.documentflow.services.exceptions.NotFoundAddressException;
import com.documentflow.services.exceptions.NotFoundEmployeeException;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @Transactional(readOnly = true)
    public List<Contragent> searchContragents(@NonNull String searchName) {

        Specification<Contragent> spec = Specification.where(null);
        spec = spec.and(ContragentSpecifications.searchNameLike(searchName));

        return contragentRepository.findAll(spec).stream()
                .filter(item -> !item.getIsDeleted())
                .collect(Collectors.toList());
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
                String searchStringPerson = ContragentUtils.createSearchName(person.getFirstName(), person.getMiddleName(), person.getLastName());
                List<Contragent> contragentsPerson = addressesPerson.stream()
                        .map(address -> {
                            Contragent contragent = new Contragent.Builder()
                                    .searchName(searchStringPerson)
                                    .isDeleted(false)
                                    .address(address)
                                    .person(person)
                                    .build();
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
                    throw new NotFoundAddressException();
                } else if (!ObjectUtils.isEmpty(employees)) {
                    addressesOrganization.forEach(
                            address -> {
                                employees.forEach(
                                        (employee, position) -> {
                                            String searchName = ContragentUtils.createSearchName(employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), position, organization.getName());
                                            Contragent contragent = new Contragent.Builder()
                                                    .searchName(searchName)
                                                    .personPosition(ContragentUtils.toUpperCase(position))
                                                    .address(address)
                                                    .person(employee)
                                                    .organization(organization)
                                                    .isDeleted(false)
                                                    .build();
                                            address.getContragents().add(contragent);
                                            employee.getContragents().add(contragent);
                                            contragentsOrganization.add(contragent);
                                        }
                                );
                            });
                } else {
                    addressesOrganization.forEach(
                            address -> {
                                String searchName = ContragentUtils.createSearchName(organization.getName());
                                Contragent contragent = new Contragent.Builder()
                                        .searchName(searchName)
                                        .address(address)
                                        .organization(organization)
                                        .build();
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
    public Contragent updateEmployee(ContragentDtoEmployee employee) {

        Optional<Contragent> optionalContragent = contragentRepository.findById(Long.valueOf(employee.getId()));
        if (!optionalContragent.isPresent()) {
            throw new NotFoundEmployeeException();
        }
        Contragent contragent = optionalContragent.get();

        StringBuilder oldPartOfTheString = new StringBuilder();
        if (contragent.getPerson() != null) {
            Person person = contragent.getPerson();
            oldPartOfTheString.append(person.getFirstName());
            oldPartOfTheString.append(person.getMiddleName());
            oldPartOfTheString.append(person.getLastName());
        }
        if (contragent.getPersonPosition() != null) {
            oldPartOfTheString.append(contragent.getPersonPosition());
        }
        String newPartOfTheString = ContragentUtils.createSearchName(employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), employee.getPersonPosition());
        String oldSearchName = contragent.getSearchName();
        String newSearchName = oldSearchName.replace(oldPartOfTheString, newPartOfTheString);

        String position = ContragentUtils.toUpperCase(employee.getPersonPosition());
        contragent.setPersonPosition(position);
        contragent.setSearchName(newSearchName);

        Person person = contragent.getPerson();
        person.setFirstName(employee.getFirstName());
        person.setMiddleName(employee.getMiddleName());
        person.setLastName(employee.getLastName());
        contragent.setPerson(person);
        return contragentRepository.save(contragent);
    }

    @Override
    public Contragent save(Contragent contragent) {
        return contragentRepository.save(contragent);
    }

    @Override
    public void delete(Long id) {
        Optional<Contragent> optionalContragent = contragentRepository.findById(id);
        if (!optionalContragent.isPresent()) {
            throw new NotFoundAddressException();
        }
        Contragent contragent = optionalContragent.get();
        contragent.setIsDeleted(true);
        contragentRepository.save(contragent);
    }

    @Override
    public Address bindAddressWithPerson(Long idPerson, Address adr) {

        Address address = addressService.save(ContragentUtils.normalizeAddress(adr));
        Person person = personService.find(idPerson);

        String searchName = ContragentUtils.createSearchName(person.getFirstName(), person.getMiddleName(), person.getLastName());
        Contragent contragent = new Contragent.Builder()
                .address(address)
                .person(person)
                .searchName(searchName)
                .isDeleted(false)
                .build();
        contragent = contragentRepository.save(contragent);

        return Address.builder()
                //ВНИМАНИЕ. Добавляем ID контрагента для комфортного удаления записи на фронте
                .id(contragent.getId())
                .index(address.getIndex())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }

    @Override
    public Address bindAddressWithOrganization(Long idOrganization, Address adr) {

        Address address = addressService.save(ContragentUtils.normalizeAddress(adr));
        Organization organization = organizationService.find(idOrganization);

        String searchName = ContragentUtils.createSearchName(organization.getName());
        Contragent contragent = new Contragent.Builder()
                .address(address)
                .organization(organization)
                .searchName(searchName)
                .isDeleted(false)
                .build();
        contragent = contragentRepository.save(contragent);

        return Address.builder()
                //ВНИМАНИЕ. Добавляем ID контрагента для комфортного удаления записи на фронте
                .id(contragent.getId())
                .index(address.getIndex())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }

    @Override
    public ContragentDtoEmployee bindEmployeeWithOrganization(Long idOrganization, ContragentDtoEmployee employee) {

        Person person = personService.save(new Person(employee.getFirstName(), employee.getMiddleName(), employee.getLastName()));
        Organization organization = organizationService.find(idOrganization);

        String searchName = ContragentUtils.createSearchName(person.getFirstName(), person.getMiddleName(), person.getLastName(), employee.getPersonPosition(), organization.getName());
        Contragent contragent = new Contragent.Builder()
                .personPosition(ContragentUtils.toUpperCase(employee.getPersonPosition()))
                .organization(organization)
                .person(person)
                .searchName(searchName)
                .isDeleted(false)
                .build();
        contragent = contragentRepository.save(contragent);

        return ContragentDtoEmployee.builder()
                //ВНИМАНИЕ. Добавляем ID контрагента для комфортного удаления записи на фронте
                .id(contragent.getId().toString())
                .firstName(person.getFirstName())
                .middleName(person.getMiddleName())
                .lastName(person.getLastName())
                .personPosition(contragent.getPersonPosition())
                .build();
    }

    @Override
    public ContragentDtoBindAddressAndEmployee bindEmployeeWithAddress(ContragentDtoBindAddressAndEmployee addressAndEmployee) {

        Organization organization = organizationService.find(addressAndEmployee.getId());
        Address address = Address.builder()
                .index(NumberUtils.toInt(addressAndEmployee.getAddress().getPostIndex()))
                .country(addressAndEmployee.getAddress().getCountry())
                .city(addressAndEmployee.getAddress().getCity())
                .street(addressAndEmployee.getAddress().getStreet())
                .houseNumber(addressAndEmployee.getAddress().getHouseNumber())
                .apartmentNumber(addressAndEmployee.getAddress().getApartrmentNumber())
                .build();
        ContragentUtils.normalizeAddress(address);

        Person person = new Person(
                addressAndEmployee.getEmployee().getFirstName(),
                addressAndEmployee.getEmployee().getMiddleName(),
                addressAndEmployee.getEmployee().getLastName()
        );

        address = addressService.save(address);
        person = personService.save(person);

        String searchName = ContragentUtils.createSearchName(
                addressAndEmployee.getEmployee().getFirstName(),
                addressAndEmployee.getEmployee().getMiddleName(),
                addressAndEmployee.getEmployee().getLastName(),
                addressAndEmployee.getEmployee().getPersonPosition(),
                organization.getName()
        );
        Contragent contragent = new Contragent.Builder()
                .searchName(searchName)
                .isDeleted(false)
                .personPosition(ContragentUtils.toUpperCase(addressAndEmployee.getEmployee().getPersonPosition()))
                .person(person)
                .address(address)
                .organization(organization)
                .build();
        contragent = contragentRepository.save(contragent);

        return new ContragentDtoBindAddressAndEmployee(
                null,
                new ContragentDtoAddress(
                        contragent.getId().toString(),
                        String.valueOf(address.getIndex()),
                        address.getCountry(),
                        address.getCity(),
                        address.getStreet(),
                        address.getHouseNumber(),
                        address.getApartmentNumber()),
                new ContragentDtoEmployee(
                        contragent.getId().toString(),
                        person.getFirstName(),
                        person.getMiddleName(),
                        person.getLastName(),
                        contragent.getPersonPosition()
                ));
    }
}
