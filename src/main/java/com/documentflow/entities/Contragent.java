package com.documentflow.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
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

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "organiztion_id")
    private Long organiztionId;

    public Contragent(Long personId, Long organiztionId, Long addressId, String searchName, String personPosition){
        this.personId = personId;
        this.organiztionId = organiztionId;
        this.addressId = addressId;
        this.searchName = searchName;
        this.personPosition = personPosition;
        this.isDeleted = false;
    }

    public Contragent(Long personId, String searchName, Long addressId){
        this(personId, null, null, searchName, null);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organiztion_id")
    private Organization organization;
}
