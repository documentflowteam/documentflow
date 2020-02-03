package com.documentflow.repositories;

import com.documentflow.entities.DocType;
import com.documentflow.entities.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTypeRepository extends JpaRepository <TaskType, Integer> {

    List<TaskType> findAll();

    TaskType findOneById(int id);

    TaskType findOneByBusinessKey(String businessKey);

}
