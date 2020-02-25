package com.documentflow.repositories.specifications;

import com.documentflow.entities.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecifications {
    public static Specification<Person> firstNameEq(String firstName){
        return (Specification<Person>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), firstName);
    }
    public static Specification<Person> middleNameEq(String middleName){
        return (Specification<Person>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("middleName"), middleName);
    }
    public static Specification<Person> lastNameEq(String lastName){
        return (Specification<Person>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastName"), lastName);
    }
}