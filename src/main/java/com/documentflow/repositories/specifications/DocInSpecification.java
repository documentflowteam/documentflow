package com.documentflow.repositories.specifications;

import com.documentflow.entities.DocIn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DocInSpecification {

    public static Specification<DocIn> regNumberContains(String regNumber) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("regNumber")),
                        criteriaBuilder.lower(criteriaBuilder.literal("%" + regNumber + "%"))));
    }

    public static Specification<DocIn> regDateGreaterThanOrEq(LocalDateTime date) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("regDate"), date));
    }

    public static Specification<DocIn> regDateLesserThanOrEq(LocalDateTime date) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("regDate"), date));
    }

    public static Specification<DocIn> docTypeId(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("docType").get("id"), id));
    }

    public static Specification<DocIn> senderContains(String sender) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("sender")),
                        criteriaBuilder.lower(criteriaBuilder.literal("%" + sender + "%"))));
    }

    public static Specification<DocIn> stateId(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("state").get("id"), id));
    }

    public static Specification<DocIn> stateIdNotEqual(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("state").get("id"), id));
    }
}
