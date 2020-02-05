package com.documentflow.repositories;

import com.documentflow.entities.DocOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long> {

    List<DocOut> findAll();

    DocOut findOneById(Long id);

    //DocOut findOneByUserId(Long userId);

    DocOut findByCreateDate (LocalDateTime createDate);

    DocOut findByRegDate (LocalDateTime regDate);

    DocOut findByContent (String content);

    DocOut findByAppendix (String appendix);

    DocOut findByNote (String note);

    DocOut findByIsGenerated (Boolean isGenerated);

    DocOut findByStateId (Integer stateId);


}