package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User getCurrentUser(int userId);

    User getBoss(int userId);

    Department getDepartmentByUserId(int userId);

    List<User> getAllUsers();

    Page<User> getPageOfUsersBySpecification(Pageable pageable);

    String getInitials(User user);
}
