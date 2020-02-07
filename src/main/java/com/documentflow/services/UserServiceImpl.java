package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.entities.User;
import com.documentflow.repositories.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Setter(onMethod_ = {@Autowired})
    private UserRepository userRepository;

    @Override
    public User getUserById(int userId) {
        return userRepository.findOneById(userId);
    }

    @Override
    public User getBossByUserId(int userId) {
        return userRepository.findOneById(userId).getBoss();
    }

    @Override
    public Department getDepartmentByUserId(int userId) {
        return userRepository.findOneById(userId).getDepartment();
    }
}
