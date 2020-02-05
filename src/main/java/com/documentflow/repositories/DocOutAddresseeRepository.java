package com.documentflow.repositories;

import com.documentflow.entities.DocOutAddressee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocOutAddresseeRepository extends JpaRepository<DocOutAddressee, Long> {

    List<DocOutAddressee> findAll();

    DocOutAddressee findOneById(Long id);

    DocOutAddressee findOneByName(String name);
}