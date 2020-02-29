package com.documentflow.services;

import com.documentflow.entities.Task;
import com.documentflow.entities.TaskHistory;
import com.documentflow.repositories.TaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryService {
    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public void setTaskHistoryRepository(TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
    }

    public List<TaskHistory> findAll() {
        return taskHistoryRepository.findAll();
    }

    public List<TaskHistory> findAllByTask(Task task) {
        return taskHistoryRepository.findAllByTask(task);
    }

    public TaskHistory save(TaskHistory taskHistory) {
        return taskHistoryRepository.save(taskHistory);
    }

}
