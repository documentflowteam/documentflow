package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import com.documentflow.exceptions.NotFoundIdException;
import com.documentflow.exceptions.NotFoundPersonException;
import com.documentflow.repositories.PersonRepository;
import com.documentflow.repositories.specifications.PersonSpecifications;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ContragentService contragentService;

    @Override
    public Person save(@NonNull ContragentDtoParameters contragentDto) {

        String firstName = ContragentUtils.toUpperCase(contragentDto.getFirstName());
        String middleName = ContragentUtils.toUpperCase(contragentDto.getMiddleName());
        String lastName = ContragentUtils.toUpperCase(contragentDto.getLastName());

        Person personWithoutId = new Person(firstName, middleName, lastName);
        return checkDuplicateAndGetPerson(personWithoutId);
    }

    @Override
    public Person save(Person person) {
        return checkDuplicateAndGetPerson(person);
    }

    @Override
    public Map<Person, String> save(ContragentDtoEmployee[] employees) {

        return Arrays.stream(employees)
                .filter(ContragentUtils::isNotEmpty)
                .collect(Collectors.toMap(
                        (ContragentDtoEmployee key) -> {
                            Person person = new Person(key.getFirstName(), key.getMiddleName(), key.getLastName());
                            return checkDuplicateAndGetPerson(person);
                        },
                        ContragentDtoEmployee::getPersonPosition
                ));
    }

    @Override
    public Person find(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new NotFoundPersonException();
        }
        return optionalPerson.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll(String firstName, String middleName, String lastName) {
        return personRepository.findAll(getSpecification(firstName, middleName, lastName)).stream()
                .filter(person -> {
                    return person.getContragents().stream()
                            .anyMatch(c -> c.getOrganization() == null && !c.getIsDeleted());
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContragentDtoEmployee> findAllEmployee(String firstName, String middleName, String lastName, String position) {

        final String employeePosition = position == null ? null : position.toUpperCase();
        List<ContragentDtoEmployee> result = new ArrayList<>();

        personRepository.findAll(getSpecification(firstName, middleName, lastName)).stream()
                .filter(person -> {
                    return person.getContragents().stream()
                            //физ.лицо привязано к какой-либо организации(т.е. является сотрудником) + запись не удалена
                            .anyMatch(c -> c.getOrganization() != null && !c.getIsDeleted());
                }).forEach(employee -> {
            employee.getContragents().stream()
                    .filter(contragent -> {
                        if (StringUtils.isEmpty(employeePosition)) {
                            return true;
                        }
                        return employeePosition.equals(contragent.getPersonPosition());
                    })
                    .forEach(contragent -> {
                        result.add(
                                new ContragentDtoEmployee(contragent.getId().toString(),
                                        employee.getFirstName(),
                                        employee.getMiddleName(),
                                        employee.getLastName(),
                                        contragent.getPersonPosition()
                                ));
                    });

        });
        return result;
    }

    @Override
    public Person strongFind(Person person) {
        Specification<Person> spec = Specification.where(null);
        if (StringUtils.isNotEmpty(person.getFirstName())) {
            spec = spec.and(PersonSpecifications.firstNameEq(person.getFirstName().toUpperCase()));
        } else {
            spec = spec.and(PersonSpecifications.firstNameEq(""));
        }
        if (StringUtils.isNotEmpty(person.getMiddleName())) {
            spec = spec.and(PersonSpecifications.middleNameEq(person.getMiddleName().toUpperCase()));
        } else {
            spec = spec.and(PersonSpecifications.middleNameEq(""));
        }
        if (StringUtils.isNotEmpty(person.getLastName())) {
            spec = spec.and(PersonSpecifications.lastNameEq(person.getLastName().toUpperCase()));
        }
        return personRepository.findOne(spec).orElse(null);
    }

    @Override
    public Person update(Person per) {

        if (per.getId() == null) {
            throw new NotFoundIdException();
        }

        Optional<Person> optionalPerson = personRepository.findById(per.getId());
        if (!optionalPerson.isPresent()) {
            throw new NotFoundPersonException();
        }
        Person person = optionalPerson.get();

        String oldFirstName = person.getFirstName();
        String oldMiddleName = person.getMiddleName();
        String oldLastName = person.getLastName();

        String newFirstName = ContragentUtils.toUpperCase(per.getFirstName());
        String newMiddleName = ContragentUtils.toUpperCase(per.getMiddleName());
        String newLastName = ContragentUtils.toUpperCase(per.getLastName());

        String oldFIO = oldFirstName + oldMiddleName + oldLastName;
        String newFIO = newFirstName + newMiddleName + newLastName;

        person.setFirstName(newFirstName);
        person.setMiddleName(newMiddleName);
        person.setLastName(newLastName);

        person.getContragents().forEach(
                contragent -> {
                    String oldSearchName = contragent.getSearchName();
                    String newSearchName = oldSearchName.replace(oldFIO, newFIO);
                    contragent.setSearchName(newSearchName);
                    contragentService.save(contragent);
                }
        );
        return personRepository.save(person);
    }

    @Override
    public void delete(Long id) {

        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new NotFoundPersonException();
        }
        Person person = optionalPerson.get();

        person.getContragents().forEach(contragent -> {
            contragent.setIsDeleted(true);
            contragentService.save(contragent);
        });
    }

    @Override
    public List<Address> getAddresses(Long id) {

        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new NotFoundPersonException();
        }

        Person person = optionalPerson.get();

        //берем список записей, которые не помечены как удаленные
        List<Contragent> contragents = person.getContragents().stream()
                .filter(contragent -> !contragent.getIsDeleted())
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

    private Specification<Person> getSpecification(String firstName, String middleName, String lastName) {
        Specification<Person> spec = Specification.where(null);
        if (StringUtils.isNotEmpty(firstName)) {
            spec = spec.and(PersonSpecifications.firstNameEq(firstName.toUpperCase()));
        }
        if (StringUtils.isNotEmpty(middleName)) {
            spec = spec.and(PersonSpecifications.middleNameEq(middleName.toUpperCase()));
        }
        if (StringUtils.isNotEmpty(lastName)) {
            spec = spec.and(PersonSpecifications.lastNameEq(lastName.toUpperCase()));
        }
        return spec;
    }

    /**
     * We check the person for presence in the database.
     * If the person exists, we take it, if it does not exist, we save it
     *
     * @param person person to check and save
     * @return Person the person was found or saved
     */
    private Person checkDuplicateAndGetPerson(Person person) {
        Person findPerson = strongFind(person);
        Person correctPerson;

        if (findPerson != null) {
            correctPerson = findPerson;
        } else {
            correctPerson = personRepository.save(ContragentUtils.normalizePerson(person));
        }

        return correctPerson;
    }
}
