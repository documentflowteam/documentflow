package com.documentflow.services;

import com.documentflow.entities.TaskType;
import com.documentflow.repositories.TaskTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTypeService {

    private TaskTypeRepository taskTypeRepository;

    @Autowired
    public void setTaskTypeRepository(TaskTypeRepository taskTypeRepository) {
        this.taskTypeRepository = taskTypeRepository;
    }

    public TaskType getTaskTypeById (int id) {
        return taskTypeRepository.findOneById(id);
    }

    public TaskType getTaskTypeByBusinessKey (String business_key) {
        return taskTypeRepository.findOneByBusinessKey(business_key);
    }

    public List<TaskType> findAllTaskTypes() {
        return taskTypeRepository.findAll();
    }


}
