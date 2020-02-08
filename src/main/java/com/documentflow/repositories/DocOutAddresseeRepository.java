package com.documentflow.repositories;

import com.documentflow.entities.DocOutAddressee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocOutAddresseeRepository extends JpaRepository<DocOutAddressee, Long>, PagingAndSortingRepository<DocOutAddressee, Long> {

    List<DocOutAddressee> getAllByName();

    List<Long> getAllById();

    DocOutAddressee findOneById(Long id);

    DocOutAddressee findOneByName(String name);

    Page<DocOutAddressee> findAll(Pageable pageable);
}