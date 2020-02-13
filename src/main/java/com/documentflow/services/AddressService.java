package com.documentflow.services;

import com.documentflow.entities.dto.ContragentDtoAddress;

import java.util.List;

public interface AddressService {

    /**
     * Checks the existence of an address in the database.
     * If there is no address in the database, it adds the address to the database.
     *
     * @param addresses array of addresses wrapped in DTO
     * @return list of ID addresses
     */
    List<Long> save(ContragentDtoAddress[] addresses);
}
