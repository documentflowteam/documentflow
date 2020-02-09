package com.documentflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "contragents")
public class Contragent implements Serializable {
    private static final long serialVersionUID = -3584625726642093828L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "person_position")
    private String personPosition;

    @Column(name = "search_name")
    private String searchName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Adress adress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organiztion_id")
    private Organization organization;

    public String toString() {
        return String.format("id - %d, person_position - %s, search_name - %s", id, personPosition, searchName);
    }
}
