package com.documentflow.controllers.rest;

import com.documentflow.entities.Department;
import com.documentflow.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/department")
public class DepartmentRestController {

    private DepartmentService service;

    @Autowired
    public void setService(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Department>> getAll() {
        return new ResponseEntity<>(service.findAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getOne(@PathVariable(name = "id") Integer id) {
        return service.existById(id)
                ? new ResponseEntity<>(service.getDepartmentById(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
