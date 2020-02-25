package com.documentflow.entities;

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
@Table(name = "organizations")
public class Organization implements Serializable {
    private static final long serialVersionUID = 3083485161177867146L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name company can not be empty")
    private String name;

    @JsonCreator
    public Organization(
            @JsonProperty("id") Long id,
            @JsonProperty("name_company") String name) {
        this.id = id;
        this.name = name;
    }

    public Organization(String name) {
        this(null, name);
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "organiztion_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "organiztion_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contragent> contragents = new ArrayList<>();
}
