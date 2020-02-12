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
@Table(name = "organizations")
public class Organization implements Serializable {
    private static final long serialVersionUID = 3083485161177867146L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

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

    public String toString() {
        return String.format("id - %d, name - %s", id, name);
    }
}
