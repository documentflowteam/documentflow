package com.documentflow.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_doc_types")
@Getter
@Setter
@NoArgsConstructor
public class DocType implements Serializable {
    private static final long serialVersionUID = -1388720840454936780L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "business_key")
    private String businessKey;

    public DocType(Long id, String name, String businessKey) {
        this.id = id;
        this.name = name;
        this.businessKey = businessKey;
    }
}
