package com.documentflow.repositories;

import com.documentflow.entities.Contragent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContragentRepository extends JpaRepository<Contragent, Long>, JpaSpecificationExecutor<Contragent> {
}
