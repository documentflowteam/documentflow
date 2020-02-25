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

import java.time.LocalDate;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long>, PagingAndSortingRepository<DocOut, Long> {

//    List<DocOut> findAll();
    Page<DocOut> findAll(Pageable pageable);

    DocOut findOneById(Long id);

    Page<DocOut> findAllByCreator(User creator, Pageable pageable);

    Page<DocOut> findAllBySigner(User signer, Pageable pageable);

    Page<DocOut> findAllByCreateDate (LocalDate createDate, Pageable pageable);

    Page<DocOut> findAllByRegDate (LocalDate regDate, Pageable pageable);

      Page<DocOut> findAllByContent (String content, Pageable pageable);

      Page<DocOut> findAllByAppendix (String appendix, Pageable pageable);

      Page<DocOut> findAllByNote (String note, Pageable pageable);

      Page<DocOut> findAllByIsGenerated (Boolean isGenerated, Pageable pageable);

      Page<DocOut> findAllByNumber (String number, Pageable pageable);

      Page<DocOut> findAllByState (State state, Pageable pageable);

      Page<DocOut> findAllByTask (Task task, Pageable pageable);

 //    <S extends DocOut> S save(S var1);
//
//      default DocOut update(DocOut docOut){
//          this.getCreator() = creator;
//          this.signer = signer;
//          this.content = content;
//          this.pages = pages;
//          this.appendix = appendix;
//          this.note = note;
//          this.state = state;
//      }

      void deleteById(Long id);

      void delete(DocOut docOut);

      User getByCreator(User user);


}