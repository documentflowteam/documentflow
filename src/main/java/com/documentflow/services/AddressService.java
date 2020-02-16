package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoAddress;

import java.util.List;

public interface AddressService {

    /**
     * Adds addresses to the database
     *
     * @param addresses array of addresses wrapped in DTO
     * @return list of addresses or null
     */
    List<Address> save(ContragentDtoAddress[] addresses);

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
     * Updating data in the database
     *
     * @param id               id
     * @param postIndex        post index
     * @param country          country
     * @param city             city
     * @param street           street
     * @param houseNumber      house number
     * @param apartrmentNumber apartrment number
     * @return updated address
     */
    Address update(Long id, Integer postIndex, String country, String city, String street, String houseNumber, String apartrmentNumber);
}
