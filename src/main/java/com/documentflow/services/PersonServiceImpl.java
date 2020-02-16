package com.documentflow.services;

import com.documentflow.entities.Contragent;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import com.documentflow.repositories.PersonRepository;
import com.documentflow.repositories.specifications.PersonSpecifications;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person save(@NonNull ContragentDtoParameters contragentDto) {

        String firstName = contragentDto.getFirstName();
        String middleName = contragentDto.getMiddleName();
        String lastName = contragentDto.getLastName();

        Person personWithoutId = new Person(firstName, middleName, lastName);
        return personRepository.save(personWithoutId);
    }

    @Override
    public Map<Person, String> save(ContragentDtoEmployee[] employees) {

        return Arrays.stream(employees)
                .filter(ContragentUtils::isNotEmpty)
                .collect(Collectors.toMap(
                        (ContragentDtoEmployee key) -> {
                            Person person = new Person(key.getFirstName(), key.getMiddleName(), key.getLastName());
                            return personRepository.save(person);
                        },
                        ContragentDtoEmployee::getPersonPosition
                ));
    }

    @Override
    public List<Person> findAll(String firstName, String middleName, String lastName) {
        Specification<Person> spec = Specification.where(null);
        if(StringUtils.isNotEmpty(firstName)) {
            spec = spec.and(PersonSpecifications.firstNameEq(firstName.toUpperCase()));
        }
        if(StringUtils.isNotEmpty(middleName)) {
            spec = spec.and(PersonSpecifications.middleNameEq(middleName.toUpperCase()));
        }
        if(StringUtils.isNotEmpty(lastName)) {
            spec = spec.and(PersonSpecifications.lastNameEq(lastName.toUpperCase()));
        }

        return personRepository.findAll(spec).stream()
                .filter( person -> {
                    return person.getContragents().stream()
                            .anyMatch(c -> c.getOrganization() == null);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Person update(Long id, String firstName, String middleName, String lastName) {
        return personRepository.save(new Person(id, firstName, middleName, lastName));
    }
}
