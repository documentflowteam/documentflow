package com.documentflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public static class Builder {
        private Long id;
        private String personPosition;
        private String searchName;
        private Boolean isDeleted;
        private Address address;
        private Person person;
        private Organization organization;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder personPosition(String personPosition) {
            this.personPosition = personPosition;
            return this;
        }

        public Builder searchName(String searchName) {
            this.searchName = searchName;
            return this;
        }

        public Builder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        public Builder organization(Organization organization) {
            this.organization = organization;
            return this;
        }

        public Contragent build() {
            Contragent contragent = new Contragent();
            contragent.id = this.id;
            contragent.personPosition = this.personPosition;
            contragent.searchName = this.searchName;
            contragent.isDeleted = this.isDeleted;
            contragent.address = this.address;
            contragent.person = this.person;
            contragent.organization = this.organization;
            return contragent;
        }
    }
}
