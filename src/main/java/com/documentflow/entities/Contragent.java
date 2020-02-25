package com.documentflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

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

    public Contragent(String searchName, String personPosition) {
        this.searchName = ObjectUtils.isEmpty(searchName) ? null : searchName.toUpperCase();
        this.personPosition = ObjectUtils.isEmpty(personPosition) ? null : personPosition.toUpperCase();
        this.isDeleted = false;
    }

    public Contragent(String searchName) {
        this(searchName, null);
    }

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "person_id")
    private Person person;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "organiztion_id")
    private Organization organization;
}
