package com.documentflow.services;

import com.documentflow.entities.DTOs.DepartmentDTO;
import com.documentflow.entities.DTOs.DocInDTO;
import com.documentflow.entities.Department;
import com.documentflow.entities.DocIn;
import com.documentflow.repositories.DepartmentRepository;
import com.documentflow.repositories.DocInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public Department convertFromDTO(DepartmentDTO departmentDTO){
        return new Department(departmentDTO.getId(),
                departmentDTO.getName(),
                departmentDTO.isActive());
    }

    public DepartmentDTO convertToDTO(Department department){
        return new DepartmentDTO(department.getId(),
                department.getName(),
                department.isActive());
    }
}
