package com.documentflow.repositories;

import com.documentflow.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Integer> {

    List<Department> findAllByOrderByName();

    Department findOneById(int id);

}
