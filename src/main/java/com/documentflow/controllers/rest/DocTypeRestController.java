package com.documentflow.controllers.rest;

import com.documentflow.entities.DocType;
import com.documentflow.services.DocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/docType")
public class DocTypeRestController {

    private DocTypeService service;

    @Autowired
    public void setService(DocTypeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<DocType>> getAll() {
        return new ResponseEntity<>(service.findAllDocTypes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocType> getOne(@PathVariable(name = "id") Integer id) {
        return service.existById(id)
                ? new ResponseEntity<>(service.getDocTypeById(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
