package com.documentflow.services;

public interface OrganizationService {

    /**
     * Checks the existence of an organization in the database.
     * If there is no organization in the database, it adds the organization to the database.
     *
     * @param organization organization
     * @return list of ID organization
     */
    Long save(String organization);
}
