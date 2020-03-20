package com.documentflow.repositories.specifications;

import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecification {
    public static Specification<Task> executor(User executor) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("executor"), executor));
    }

    public static Specification<Task> author(User author) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author"), author));
    }
}
