package com.documentflow.repositories;

import com.documentflow.entities.DocType;
import com.documentflow.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocTypeRepository extends JpaRepository <DocType, Integer> {

    List<DocType> findAll();

    DocType findOneById(int id);

    DocType findOneByBusinessKey(String businessKey);

}
