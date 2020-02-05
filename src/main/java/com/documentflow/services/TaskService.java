package com.documentflow.services;

import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import com.documentflow.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findOneById(Long id) {
        return taskRepository.getOne(id);
    }

    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Task> findAllByAuthor(User author) {
        return taskRepository.findAllByAuthor(author);
    }

    public List<Task> findAllByExecutor(User executor) {
        return taskRepository.findAllByExecutor(executor);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

}
