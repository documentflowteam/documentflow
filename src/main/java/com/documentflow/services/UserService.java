package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getCurrentUser(int userId);

    User getBoss(int userId);

    Department getDepartmentByUserId(int userId);

    List<User> getAllUsers();

    Page<User> getPageOfUsers(Pageable pageable);

    User getUserByUsername(String username);

    String getInitials(User user);

    boolean isActive(User user);

    User findOneById(int id);

    void delete(User user);

    User saveOrUpdate(User user);

//    boolean save(SystemUser systemUser);

    boolean isExistsUsername(String username);

}
