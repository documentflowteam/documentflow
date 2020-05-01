package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.entities.dto.ContragentDtoBindAddressAndEmployee;
import com.documentflow.entities.dto.ContragentDtoEmployee;

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
     * @param employee dto object
     * @return updated person
     */
    Contragent updateEmployee(ContragentDtoEmployee employee);

    /**
     * Update contragent
     *
     * @param contragent contragent with changed data that needs to be saved
     * @return updated contragent
     */
    Contragent save(Contragent contragent);

    /**
     * Assign the archive attribute to the record
     *
     * @param id id record
     */
    void delete(Long id);

    /**
     * Link the address to the person
     *
     * @param idPerson id person
     * @param address  address
     * @return address
     */
    Address bindAddressWithPerson(Long idPerson, Address address);

    /**
     * Link the address to the organization
     *
     * @param idOrganization id organization
     * @param address        address
     * @return address
     */
    Address bindAddressWithOrganization(Long idOrganization, Address address);

    /**
     * Link the employee to the organization
     *
     * @param idOrganization id organization
     * @param employee       employee Dto
     * @return ContragentDtoEmployee
     */
    ContragentDtoEmployee bindEmployeeWithOrganization(Long idOrganization, ContragentDtoEmployee employee);

    /**
     * Link the employee with address
     *
     * @param addressAndEmployee dto object
     * @return ContragentDtoEmployee
     */
    ContragentDtoBindAddressAndEmployee bindEmployeeWithAddress(ContragentDtoBindAddressAndEmployee addressAndEmployee);
}
