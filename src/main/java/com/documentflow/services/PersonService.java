package com.documentflow.services;

import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;

import java.util.List;
import java.util.Map;

public interface PersonService {

    /**
     * Adds a person to the database
     *
     * @param person person
     * @return person with ID
     */
    Person save(ContragentDtoParameters person);

    /**
     * Adds a contact person of the organization to the database
     *
     * @param person a contact person of the organization
     * @return map where key is person id and value is position in company
     */
    Map<Person, String> save(ContragentDtoEmployee[] person);

    /**
     * We search for all persons who meet the search conditions
     *
     * @param firstName  first name
     * @param middleName middle name
     * @param lastName   last name
     * @return list of persons
     */
    List<Person> findAll(String firstName, String middleName, String lastName);

    /**
     * Updating data in the database
     *
     * @param person person
     * @return updated person
     */
    Person update(Person person);

    /**
     * Delete data by id
     *
     * @param id of the item to delete
     */
    void delete(Long id);
}
