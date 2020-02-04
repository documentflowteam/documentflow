package com.documentflow.repositories;

import com.documentflow.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository <Department, Integer> {

    List<Department> findAllByOrderByName();

    Department findOneById(int id);

}
