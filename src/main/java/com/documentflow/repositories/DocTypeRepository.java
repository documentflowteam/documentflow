package com.documentflow.repositories;

import com.documentflow.entities.DocIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTypeRepository extends JpaRepository<DocIn, Long> {
}
