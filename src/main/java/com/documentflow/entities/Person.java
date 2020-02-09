package com.documentflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "persons")
public class Person implements Serializable {
    private static final long serialVersionUID = 5203811427409523491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "organiztion_id"))
    private List<Organization> organizations;

    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Adress> adresses;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Collection<Contragent> contragents;

    public String toString() {
        return String.format("id - %d, first_name - %s, middle_name - %s, last_name - %s", id, firstName, middleName, lastName);
    }
}
