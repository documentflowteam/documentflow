package com.documentflow.services;

import com.documentflow.entities.Contragent;

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
     * Checks the existence of an contragent in the database.
     * If there is no contragent in the database, it adds the contragent to the database.
     *
     * @param contragents list of contragents
     * @return list of ID contragents
     */
    List<Long> save(List<Contragent> contragents);
}
