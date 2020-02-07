package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.entities.User;

public interface UserService {
    User getUserById(int userId);

    User getBossByUserId(int userId);

    Department getDepartmentByUserId(int userId);
}
