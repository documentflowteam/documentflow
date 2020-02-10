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

    List<DocOutAddressee> getAllByName(String name);

    List<DocOutAddressee> getAllById(Long id);

    List<DocOutAddressee> getAllByAddress(String address);

 //   Page<DocOutAddressee> getAll(Pageable pageable);

 //   DocOutAddressee findOneById(Long id);

    DocOutAddressee getDocOutAddresseeByName(String name);

    DocOutAddressee getDocOutAddresseeById(Long id);

    Page<DocOutAddressee> findAll(Pageable pageable);

//    Map<Long, String> findAllByIdAndName(Long id, String name);
}