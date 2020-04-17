package com.documentflow.utils;

import com.documentflow.entities.DocOut;
import com.documentflow.repositories.specifications.DocOutSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Getter
public class DocOutFilter {
    private Specification<DocOut> specification;
    private StringBuilder filtersString;

    public DocOutFilter(HttpServletRequest request) {
        filtersString = new StringBuilder();
        specification = Specification.where(null);

        if (request.getParameter("number") != null && !request.getParameter("number").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.numberContains(request.getParameter("number")));
            filtersString.append("&number=" + request.getParameter("number"));
        }

        if (request.getParameter("createDateMin") != null && !request.getParameter("createDateMin").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.createDateGreaterThanOrEq(LocalDateTime.parse(request.getParameter("createDateMin"))));
            filtersString.append("&createDateMin=" + request.getParameter("createDateMin"));
        }

        if (request.getParameter("createDateMax") != null && !request.getParameter("createDateMax").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.createDateLesserThanOrEq(LocalDateTime.parse(request.getParameter("regDateMax"))));
            filtersString.append("&createDateMax=" + request.getParameter("createDateMax"));
        }

        if (request.getParameter("regDateMin") != null && !request.getParameter("regDateMin").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.regDateGreaterThanOrEq(LocalDateTime.parse(request.getParameter("regDateMin"))));
            filtersString.append("&regDateMin=" + request.getParameter("regDateMin"));
        }

        if (request.getParameter("regDateMax") != null && !request.getParameter("regDateMax").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.regDateLesserThanOrEq(LocalDateTime.parse(request.getParameter("regDateMax"))));
            filtersString.append("&regDateMax=" + request.getParameter("regDateMax"));
        }

        if (request.getParameter("docTypeId") != null && !request.getParameter("docTypeId").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.docTypeId(Integer.valueOf(request.getParameter("docTypeId"))));
            filtersString.append("&docTypeId=" + request.getParameter("docTypeId"));
        }

        if (request.getParameter("creator") != null && !request.getParameter("creator").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.creatorContains(request.getParameter("creator")));
            filtersString.append("&creator=" + request.getParameter("creator"));
        }

        if (request.getParameter("signer") != null && !request.getParameter("signer").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.signerContains(request.getParameter("signer")));
            filtersString.append("&signer=" + request.getParameter("signer"));
        }

        if (request.getParameter("stateId") != null && !request.getParameter("stateId").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.stateId(Integer.valueOf(request.getParameter("stateId"))));
            filtersString.append("&stateId=" + request.getParameter("stateId"));
        }

        if (request.getParameter("content") != null && !request.getParameter("content").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.contentContains(request.getParameter("content")));
            filtersString.append("&content=" + request.getParameter("content"));
        }

        if (request.getParameter("note") != null && !request.getParameter("note").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.noteContains(request.getParameter("note")));
            filtersString.append("&note=" + request.getParameter("note"));
        }

        if (request.getParameter("taskId") != null && !request.getParameter("taskId").isEmpty()) {
            specification = getSpecification().and(DocOutSpecifications.taskId(Integer.valueOf(request.getParameter("taskId"))));
            filtersString.append("&taskId=" + request.getParameter("taskId"));
        }
    }
}
