package com.documentflow.services;

import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import com.documentflow.repositories.PersonRepository;
import com.documentflow.repositories.specifications.PersonSpecifications;
import com.documentflow.services.exceptions.NotFoundIdException;
import com.documentflow.services.exceptions.NotFoundPersonException;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        String firstName = contragentDto.getFirstName() != null ? contragentDto.getFirstName().toUpperCase() : null;
        String middleName = contragentDto.getMiddleName() != null ? contragentDto.getMiddleName().toUpperCase() : null;
        String lastName = contragentDto.getLastName() != null ? contragentDto.getLastName().toUpperCase() : null;

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
    @Transactional(readOnly = true)
    public List<Person> findAll(String firstName, String middleName, String lastName) {
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

        return personRepository.findAll(spec).stream()
                .filter(person -> {
                    return person.getContragents().stream()
                            .anyMatch(c -> c.getOrganization() == null && !c.getIsDeleted());
                })
                .collect(Collectors.toList());
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

        String newFirstName = StringUtils.isNotEmpty(per.getFirstName()) ? per.getFirstName().toUpperCase() : null;
        String newMiddleName = StringUtils.isNotEmpty(per.getMiddleName()) ? per.getMiddleName().toUpperCase() : null;
        String newLastName = StringUtils.isNotEmpty(per.getLastName()) ? per.getLastName().toUpperCase() : null;

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
}
