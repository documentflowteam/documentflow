package com.documentflow.repositories;

import com.documentflow.entities.DocType;
import com.documentflow.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocTypeRepository extends JpaRepository <DocType, Integer> {

    List<DocType> findAll();

    DocType findOneById(int id);

    DocType findOneByBusinessKey(String businessKey);

}
