package com.documentflow.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_states")
@Getter
@Setter
@NoArgsConstructor
public class State implements Serializable {
    private static final long serialVersionUID = -6865221398679019741L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "business_key")
    private String businessKey;

    public State(Long id, String name, String businessKey) {
        this.id = id;
        this.name = name;
        this.businessKey = businessKey;
    }
}
