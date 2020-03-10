package com.documentflow.repositories.specifications;

import com.documentflow.entities.Address;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {
    public static Specification<Address> postIndexEq(String postIndex) {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("index"), postIndex);
    }

    public static Specification<Address> postIndexIsNull() {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNull(root.get("index"));
    }

    public static Specification<Address> countryEq(String country) {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("country"), country);
    }

    public static Specification<Address> cityEq(String city) {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<Address> streetEq(String street) {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("street"), street);
    }

    public static Specification<Address> houseNumberEq(String houseNumber) {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("houseNumber"), houseNumber);
    }

    public static Specification<Address> houseNumberIsNull() {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNull(root.get("houseNumber"));
    }

    public static Specification<Address> apartmentNumberEq(String apartmentNumber) {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("apartmentNumber"), apartmentNumber);
    }

    public static Specification<Address> apartmentNumberIsNull() {
        return (Specification<Address>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNull(root.get("apartmentNumber"));
    }
}
