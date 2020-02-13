package com.documentflow.services;

import com.documentflow.entities.Person;

public interface PersonService {

    /**
     * Checks the existence of an person in the database.
     * If there is no person in the database, it adds the person to the database.
     *
     * @param person person
     * @return person with ID or null
     */
    Person save(Person person);
}
