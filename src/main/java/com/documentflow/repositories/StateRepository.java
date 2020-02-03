package com.documentflow.repositories;

import com.documentflow.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository <State, Integer> {

    List<State> findAll();

    State findOneById(int id);

    State findOneByBusinessKey(String businessKey);

}
