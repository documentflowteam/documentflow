package com.documentflow.repositories;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocInRepository extends JpaRepository<DocIn, Long>, JpaSpecificationExecutor<DocIn> {

    DocIn findByTask(Task task);
    DocIn findFirstByOrderByIdDesc();
    DocIn findByRegNumber(String regNumber);
    DocIn findByDocOut(DocOut docOut);
}
