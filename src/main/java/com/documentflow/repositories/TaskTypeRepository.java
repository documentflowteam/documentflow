package com.documentflow.repositories;

import com.documentflow.entities.DocType;
import com.documentflow.entities.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTypeRepository extends JpaRepository <TaskType, Integer> {

    List<TaskType> findAll();

    TaskType findOneById(int id);

    TaskType findOneByBusinessKey(String businessKey);

}
