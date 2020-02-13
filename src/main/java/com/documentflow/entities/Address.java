package com.documentflow.entities;

import com.documentflow.entities.dto.ContragentDtoAddress;
import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adresses")
public class Address implements Serializable  {
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

    public Address(@NonNull ContragentDtoAddress contragentDtoAddress) {
        this.index = NumberUtils.toInt(contragentDtoAddress.getPostIndex());
        this.country = contragentDtoAddress.getCountry();
        this.city = contragentDtoAddress.getCity();
        this.street = contragentDtoAddress.getStreet();
        this.houseNumber = NumberUtils.toInt(contragentDtoAddress.getHouseNumber());
        this.apartmentNumber = NumberUtils.toInt(contragentDtoAddress.getApartrmentNumber());
    }

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
}
