package com.documentflow.services;

import com.documentflow.entities.Contragent;
import com.documentflow.entities.dto.ContragentDto;

import java.util.List;

public interface ContragentService {

    /**
     * Search for a contragents that meets the search condition
     *
     * @param searchName search string (can include the person's full name or company name, or position)
     * @return null if nothing is found or list of companies/person's
     */
    List<Contragent> searchContragents(String searchName);

    /**
     * We record the counterparty and related entities (address, employee, organization, or individual) in the database)
     *
     * @param contragentDto contragentDto
     * @return list of contragents
     */
    List<Contragent> save(ContragentDto contragentDto);

    /**
     * Updating employee in the database
     *
     * @param id         id
     * @param firstName  first name
     * @param middleName middle name
     * @param lastName   last name
     * @param position   position
     * @return updated person
     */
    Contragent updateEmployee(Long id, String firstName, String middleName, String lastName, String position);

    /**
     * Update contragent
     *
     * @param contragent contragent with changed data that needs to be saved
     * @return updated contragent
     */
    Contragent save(Contragent contragent);
}
