package com.documentflow.repositories;

import com.documentflow.entities.Task;
import com.documentflow.entities.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findAllByTask(Task task);
}
