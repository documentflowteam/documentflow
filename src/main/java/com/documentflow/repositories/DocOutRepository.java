package com.documentflow.repositories;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long>, JpaSpecificationExecutor<DocOut> {

//    Page<DocOut> findAll(Specification<DocOut> specification, Pageable pageable);

    DocOut findOneById(Long id);

    void deleteById(Long id);

    void delete(DocOut docOut);

    User getByCreator(User user);

//    Page<DocOut> findAllByCreator(User creator, Pageable pageable);
//
//    Page<DocOut> findAllBySigner(User signer, Pageable pageable);
//
//    Page<DocOut> findAllByCreateDate (LocalDate createDate, Pageable pageable);
//
//    Page<DocOut> findAllByRegDate (LocalDate regDate, Pageable pageable);
//
//      Page<DocOut> findAllByContent (String content, Pageable pageable);
//
//      Page<DocOut> findAllByAppendix (String appendix, Pageable pageable);
//
//      Page<DocOut> findAllByNote (String note, Pageable pageable);
//
//      Page<DocOut> findAllByIsGenerated (Boolean isGenerated, Pageable pageable);
//
//      Page<DocOut> findAllByNumber (String number, Pageable pageable);
//
//      Page<DocOut> findAllByState (State state, Pageable pageable);
//
//      Page<DocOut> findAllByTask (Task task, Pageable pageable);

}