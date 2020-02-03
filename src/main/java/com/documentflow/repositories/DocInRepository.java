package com.documentflow.repositories;

import com.documentflow.entities.DocIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocInRepository extends JpaRepository<DocIn, Long> {
}
