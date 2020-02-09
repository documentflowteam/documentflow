package com.documentflow.repositories;

import com.documentflow.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Transactional(readOnly = true)
    List<Organization> findAll();

    @Transactional(readOnly = true)
    Optional<Organization> findById(Long inn);

    @Transactional(readOnly = true)
    Optional<List<Organization>> findAllByName(String name);
}
