package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.repositories.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentService {

    private DepartmentsRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentsRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartmentById (int id) {
        return departmentRepository.findOneById(id);
    }

    public List<Department> findAllDepartments() {
        return departmentRepository.findAllByOrderByName();
    }


}
