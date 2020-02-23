package com.documentflow.entities;

import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.utils.ContragentUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "addresses")
public class Address implements Serializable {
    private static final long serialVersionUID = 2600335316131008220L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "post_index")
    private Integer index;

    @Column(name = "country")
    @NotBlank(message = "Country can not be empty")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "City can not be empty")
    private String city;

    @Column(name = "street")
    @NotBlank(message = "Street can not be empty")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartrment_number")
    private String apartmentNumber;

    @JsonCreator
    public Address(@JsonProperty("id") Long id,
                   @JsonProperty("post_index") Integer postIndex,
                   @JsonProperty("country") String country,
                   @JsonProperty("city") String city,
                   @JsonProperty("street") String street,
                   @JsonProperty("house_number") String houseNumber,
                   @JsonProperty("apartrment_number") String apartmentNumber) {
        this.id = id;
        this.index = postIndex;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public Address(@NonNull ContragentDtoAddress contragentDtoAddress) {
        this(
                null,
                NumberUtils.toInt(contragentDtoAddress.getPostIndex()),
                ContragentUtils.toUpperCase(contragentDtoAddress.getCountry()),
                ContragentUtils.toUpperCase(contragentDtoAddress.getCity()),
                ContragentUtils.toUpperCase(contragentDtoAddress.getStreet()),
                contragentDtoAddress.getHouseNumber(),
                contragentDtoAddress.getApartrmentNumber()
        );
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "organiztion_id"))
    private List<Organization> organizations = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Contragent> contragents = new ArrayList<>();
}
