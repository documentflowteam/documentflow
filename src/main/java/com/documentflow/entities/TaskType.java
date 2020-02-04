package com.documentflow.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sys_task_types")
public class TaskType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "business_key")
    private String businessKey;

}
