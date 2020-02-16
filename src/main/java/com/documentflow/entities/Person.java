package com.documentflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public Person(Long id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = ObjectUtils.isEmpty(firstName) ? null : firstName.toUpperCase();
        this.middleName = ObjectUtils.isEmpty(middleName) ? null : middleName.toUpperCase();
        this.lastName = ObjectUtils.isEmpty(lastName) ? null : lastName.toUpperCase();
    }

    public Person(String firstName, String middleName, String lastName) {
        this(null, firstName, middleName, lastName);
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "organiztion_id"))
    private List<Organization> organizations = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Contragent> contragents = new ArrayList<>();
}
