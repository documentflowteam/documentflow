package com.documentflow.utils;

import com.documentflow.entities.Task;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;

@Getter
public class TaskFilter {
    private Specification<Task> specification;
    private StringBuilder filtersStr;

    public TaskFilter(HttpServletRequest request) {
        filtersStr = new StringBuilder();
        specification = Specification.where(null);
    }
}
