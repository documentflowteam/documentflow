package com.documentflow.repositories;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long>, JpaSpecificationExecutor<DocOut> {


    DocOut findOneById(Long id);

    void deleteById(Long id);

    void delete(DocOut docOut);

    User getByCreator(User user);

    DocOut findByTask(Task task);


}