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
@Table(name = "adresses")
public class Adress implements Serializable  {
    private static final long serialVersionUID = 2600335316131008220L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "post_index")
    private Integer index;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "apartrment_number")
    private Integer apartmentNumber;

    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "organiztion_id"))
    private List<Organization> organizations;

    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;

    @OneToMany(mappedBy = "adress", fetch = FetchType.LAZY)
    private Collection<Contragent> contragents;

    public String toString() {
        return String.format("id - %d, city - %s, street - %s, house_number - %d, apatrment_number - %d", id, city, street, houseNumber, apartmentNumber);
    }
}
