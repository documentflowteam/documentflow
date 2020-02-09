package com.documentflow.repositories;

import com.documentflow.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional(readOnly = true)
    Optional<Person> findById(Long aLong);

    @Transactional(readOnly = true)
    Optional<List<Person>> findAllByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);
}
