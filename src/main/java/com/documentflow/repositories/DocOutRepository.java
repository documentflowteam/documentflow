package com.documentflow.repositories;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.State;
import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long>, PagingAndSortingRepository<DocOut, Long> {

//    List<DocOut> findAll();
    Page<DocOut> findAll(Pageable pageable);

    DocOut findOneById(Long id);

 //   List<DocOut> findAllByCreator(User creator);
    Page<DocOut> findAllByCreator(User creator, Pageable pageable);

//    List<DocOut> findAllBySigner(User signer);
    Page<DocOut> findAllBySigner(User signer, Pageable pageable);

//    List<DocOut> findAllByCreateDate (LocalDateTime createDate);
    Page<DocOut> findAllByCreateDate (LocalDateTime createDate, Pageable pageable);

//    List<DocOut> findAllByRegDate (LocalDateTime regDate);
    Page<DocOut> findAllByRegDate (LocalDateTime regDate, Pageable pageable);

//    List<DocOut> findAllByContent (String content);
      Page<DocOut> findAllByContent (String content, Pageable pageable);

//    List<DocOut> findAllByAppendix (String appendix);
      Page<DocOut> findAllByAppendix (String appendix, Pageable pageable);

//    List<DocOut> findAllByNote (String note);
      Page<DocOut> findAllByNote (String note, Pageable pageable);

//    List<DocOut> findAllByIsGenerated (Boolean isGenerated);
      Page<DocOut> findAllByIsGenerated (Boolean isGenerated, Pageable pageable);

//    List<DocOut> findAllByState (State state);
      Page<DocOut> findAllByState (State state, Pageable pageable);

//    List<DocOut> findAllByTaskType (TaskType taskType);
      Page<DocOut> findAllByTask (Task task, Pageable pageable);

}