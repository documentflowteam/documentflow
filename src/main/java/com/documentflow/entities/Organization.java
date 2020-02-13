package com.documentflow.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "organizations")
public class Organization implements Serializable {
    private static final long serialVersionUID = 3083485161177867146L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Organization(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "organiztion_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses;

    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "organiztion_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private Collection<Contragent> contragents;
}
