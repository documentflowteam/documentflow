package com.documentflow.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StateDTO implements Serializable {
    private static final long serialVersionUID = 1774875010542486006L;

    private Long id;
    private String name;
    private String businessKey;

    public StateDTO(Long id, String name, String businessKey) {
        this.id = id;
        this.name = name;
        this.businessKey = businessKey;
    }
}
