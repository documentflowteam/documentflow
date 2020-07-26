package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartmentById (int id) {
        return departmentRepository.findOneById(id);
    }

    public List<Department> findAllDepartments() {
        return departmentRepository.findAllByOrderByName();
    }

    public boolean existById(int id) {
        return departmentRepository.existsById(id);
    }
}
