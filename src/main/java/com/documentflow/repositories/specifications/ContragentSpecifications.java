package com.documentflow.repositories.specifications;

import com.documentflow.entities.Contragent;
import org.springframework.data.jpa.domain.Specification;

public class ContragentSpecifications {
    public static Specification<Contragent> searchNameLike(String searchName) {
        return (Specification<Contragent>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("searchName"), "%" + searchName + "%");
    }
}
