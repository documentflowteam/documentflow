package com.documentflow.repositories;

import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByAuthor(User author);
    public List<Task> findAllByExecutor(User executor);
}
