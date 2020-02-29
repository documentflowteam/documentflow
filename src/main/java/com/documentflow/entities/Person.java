package com.documentflow.entities;

import com.documentflow.utils.ContragentUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Last name can no be empty")
    private String lastName;

    @JsonCreator
    public Person(
            @JsonProperty("id") Long id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("middle_name") String middleName,
            @JsonProperty("last_name") String lastName) {
        this.id = id;
        this.firstName = ContragentUtils.toUpperCase(firstName);
        this.middleName = ContragentUtils.toUpperCase(middleName);
        this.lastName = ContragentUtils.toUpperCase(lastName);
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
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contragent> contragents = new ArrayList<>();
}
