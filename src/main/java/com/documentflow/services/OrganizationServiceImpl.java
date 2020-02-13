package com.documentflow.services;

import com.documentflow.entities.Organization;
import com.documentflow.repositories.OrganizationRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Long save(@NonNull String organizationForSave) {

        //TODO реализовать проверку организации в БД на предмет существования.
        if(StringUtils.isNotEmpty(organizationForSave)){
            Organization normalizedOrganization = new Organization(StringUtils.upperCase(organizationForSave).trim());
            return organizationRepository.save(normalizedOrganization).getId();
        }
        return null;
    }
}
