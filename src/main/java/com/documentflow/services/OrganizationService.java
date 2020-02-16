package com.documentflow.services;

import com.documentflow.entities.Organization;
import com.documentflow.entities.dto.ContragentDtoParameters;

import java.util.List;

public interface OrganizationService {

    /**
     * Adds an organization to the database
     *
     * @param organization organization
     * @return organization
     */
    Organization save(ContragentDtoParameters organization);

    /**
     * We search for all organizations who meet the search conditions
     *
     * @param nameCompany name company
     * @return list of organizations
     */
    List<Organization> findAll(String nameCompany);

    /**
     * Updating data in the database
     *
     * @param id          id
     * @param nameCompany name company
     * @return updated person
     */
    Organization update(Long id, String nameCompany);
}
