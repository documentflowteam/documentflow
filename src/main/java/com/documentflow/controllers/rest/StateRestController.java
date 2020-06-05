package com.documentflow.controllers.rest;

import com.documentflow.entities.State;
import com.documentflow.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/state")
public class StateRestController {

    private StateService service;

    @Autowired
    public void setService(StateService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<State>> getAll() {
        return new ResponseEntity<>(service.findAllStates(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findOne(@PathVariable(name = "id") Integer id) {
        return service.existById(id)
                ? new ResponseEntity<>(service.getStateById(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
