package com.documentflow.repositories;

import com.documentflow.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository <State, Integer> {

    List<State> findAll();

    State findOneById(int id);

    State findOneByBusinessKey(String businessKey);

}
