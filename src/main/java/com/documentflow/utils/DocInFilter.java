package com.documentflow.utils;

import com.documentflow.entities.DocIn;
import com.documentflow.repositories.specifications.DocInSpecification;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DocInFilter {
    private Specification<DocIn> specification;
    private StringBuilder filtersStr;

    public DocInFilter(HttpServletRequest request) {
        filtersStr = new StringBuilder();
        specification = Specification.where(null);

        if (request.getParameter("regNumber") != null && !request.getParameter("regNumber").isEmpty()) {
            specification = getSpecification().and(DocInSpecification.regNumberContains(request.getParameter("regNumber")));
            filtersStr.append("&regNumber=" + request.getParameter("regNumber"));
        }

        if (request.getParameter("regDateMin") != null && !request.getParameter("regDateMin").isEmpty()) {
            specification = getSpecification().and(DocInSpecification.regDateGreaterThanOrEq(LocalDate.parse(request.getParameter("regDateMin")).atStartOfDay()));
            filtersStr.append("&regDateMin=" + request.getParameter("regDateMin"));
        }

        if (request.getParameter("regDateMax") != null && !request.getParameter("regDateMax").isEmpty()) {
            specification = getSpecification().and(DocInSpecification.regDateLesserThanOrEq(LocalDate.parse(request.getParameter("regDateMax")).atTime(23, 59)));
            filtersStr.append("&regDateMax=" + request.getParameter("regDateMax"));
        }

        if (request.getParameter("docTypeId") != null && !request.getParameter("docTypeId").isEmpty()) {
            specification = getSpecification().and(DocInSpecification.docTypeId(Integer.valueOf(request.getParameter("docTypeId"))));
            filtersStr.append("&docTypeId=" + request.getParameter("docTypeId"));
        }

        if (request.getParameter("sender") != null && !request.getParameter("sender").isEmpty()) {
            specification = getSpecification().and(DocInSpecification.senderContains(request.getParameter("sender")));
            filtersStr.append("&sender=" + request.getParameter("sender"));
        }

        if (request.getParameter("stateId") != null && !request.getParameter("stateId").isEmpty()) {
            specification = getSpecification().and(DocInSpecification.stateId(Integer.valueOf(request.getParameter("stateId"))));
            filtersStr.append("&stateId=" + request.getParameter("stateId"));
        }
    }
}
