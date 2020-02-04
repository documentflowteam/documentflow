package com.documentflow.repositories;

import com.documentflow.entities.DocOutAddressee;

import java.util.List;

public interface DocOutAddresseeRepository extends JpaRepository<DocOutAddressee, Long> {

    List<DocOutAddressee> findAll();

    DocOutAddressee findOneById(Long id);

    DocOutAddressee findOneByName(String name);
}
