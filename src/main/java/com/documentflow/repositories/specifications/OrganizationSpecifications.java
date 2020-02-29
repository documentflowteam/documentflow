package com.documentflow.repositories.specifications;

import com.documentflow.entities.Organization;
import org.springframework.data.jpa.domain.Specification;

public class OrganizationSpecifications {
    public static Specification<Organization> nameCompanyLike(String nameCompany) {
        return (Specification<Organization>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + nameCompany + "%");
    }
}
