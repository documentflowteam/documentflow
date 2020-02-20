package com.documentflow.repositories;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocInRepository extends JpaRepository<DocIn, Long> {

    DocIn findByTask(Task task);
}
