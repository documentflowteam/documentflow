package com.documentflow.repositories;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.State;
import com.documentflow.entities.TaskType;
import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long> {

    List<DocOut> findAll();

    DocOut findOneById(Long id);

    List<DocOut> findAllByCreator(User creator);

    List<DocOut> findAllBySigner(User signer);

    List<DocOut> findAllByCreateDate (LocalDateTime createDate);

    List<DocOut> findAllByRegDate (LocalDateTime regDate);

    List<DocOut> findAllByContent (String content);

    List<DocOut> findAllByAppendix (String appendix);

    List<DocOut> findAllByNote (String note);

    List<DocOut> findAllByIsGenerated (Boolean isGenerated);

    List<DocOut> findAllByState (State state);

    List<DocOut> findAllByTaskType (TaskType taskType);


}