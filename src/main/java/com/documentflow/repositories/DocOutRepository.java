package com.documentflow.repositories;

import com.documentflow.entities.Contragent;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocOutRepository extends JpaRepository<DocOut, Long>, JpaSpecificationExecutor<DocOut> {


    DocOut findOneById(Long id);

    void deleteById(Long id);

    void delete(DocOut docOut);

    List<DocOut> getByCreator(User user);

    DocOut findByTask(Task task);

    Page<DocOut> findAllByContragents(Pageable pageable, Contragent contragent);

    List<DocOut> findAllByContragents(Contragent contragent);


}