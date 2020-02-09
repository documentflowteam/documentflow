package com.documentflow.repositories;

import com.documentflow.entities.Contragent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContragentRepository extends JpaRepository<Contragent, Long> {

    @Transactional(readOnly = true)
    List<Contragent> findAllBySearchNameLike(String searchName);
}
