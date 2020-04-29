package com.documentflow.repositories.specifications;


import com.documentflow.entities.DocOut;
import com.documentflow.entities.User;
import com.documentflow.model.enums.BusinessKeyState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DocOutSpecifications {

    public static Specification<DocOut> createDateGreaterThanOrEq(LocalDateTime createDate) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), createDate));
    }

    public static Specification<DocOut> createDateLesserThanOrEq(LocalDateTime createDate) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), createDate));
    }


    public static Specification<DocOut> regDateGreaterThanOrEq(LocalDateTime regDate) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("regDate"), regDate));
    }

    public static Specification<DocOut> regDateLesserThanOrEq(LocalDateTime date) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("regDate"), date));
    }

    public static Specification<DocOut> docTypeId(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("docType").get("id"), id));
    }

//    public static Specification<DocOut> creatorContains(String creator) {
//        return ((root, criteriaQuery, criteriaBuilder) ->
//                criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get("creator")),
//                        criteriaBuilder.lower(criteriaBuilder.literal("%" + creator + "%"))));
//    }


    public static Specification<DocOut> creatorId(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("creator").get("id"), id));
    }

    public static Specification<DocOut> signerContains(String signer) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("signer")),
                        criteriaBuilder.lower(criteriaBuilder.literal("%" + signer + "%"))));
    }

    public static Specification<DocOut> numberContains(String number) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("number")),
                        criteriaBuilder.lower(criteriaBuilder.literal("%" + number + "%"))));
    }

    public static Specification<DocOut> stateId(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("state").get("id"), id));
    }

    public static Specification<DocOut> stateIdNotEqual(BusinessKeyState businessKeyState) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("state").get("businessKey"), businessKeyState.toString()));
    }

    public static Specification<DocOut> taskId(Integer id) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("task").get("id"), id));
    }

    public static Specification<DocOut> contentContains(String content) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("content")),
                        criteriaBuilder.lower(criteriaBuilder.literal("%" + content + "%"))));
    }

    public static Specification<DocOut> noteContains(String note) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("note")),
                        criteriaBuilder.lower(criteriaBuilder.literal("%" + note + "%"))));
    }
}

