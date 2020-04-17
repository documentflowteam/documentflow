package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoAddress;

import java.util.List;

public interface AddressService {

    /**
     * Adds addresses to the database
     *
     * @param addresses array of addresses wrapped in Dto
     * @return list of addresses or null
     */
    List<Address> save(ContragentDtoAddress[] addresses);

    /**
     * Add address to the database
     *
     * @param address address
     * @return address with id
     */
    Address save(Address address);

    /**
     * We search for all addresses who meet the search conditions
     *
     * @param postIndex        post index
     * @param country          country
     * @param city             city
     * @param street           street
     * @param houseNumber      house number
     * @param apartrmentNumber apartrment number
     * @return list of address
     */
    List<Address> findAll(String postIndex, String country, String city, String street, String houseNumber, String apartrmentNumber);

    /**
     * We search for address who meet the search conditions. Strict search. If the field value is
     * not specifying this field is equal to null in the database query
     *
     * @param address
     * @return address or null if not found
     */
    Address strongFind(Address address);

    /**
     * Updating data in the database
     *
     * @param address address
     * @return updated address
     */
    Address update(Address address);

    /**
     * Delete data by id
     *
     * @param id of the item to delete
     */
    void delete(Long id);
}
