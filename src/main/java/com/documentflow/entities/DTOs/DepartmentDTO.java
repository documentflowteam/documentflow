package com.documentflow.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = 1774875010542486006L;

    private Long id;
    private String name;
    private boolean isActive;

    public DepartmentDTO(Long id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }
}
