package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.entities.Role;
import com.documentflow.entities.User;
import com.documentflow.repositories.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Setter(onMethod_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser(int userId) {
        return userRepository.findOneById(userId);
    }

    @Override
    public User getBoss(int userId) {
        return userRepository.findOneById(userId).getBoss();
    }

    @Override
    public Department getDepartmentByUserId(int userId) {
        return userRepository.findOneById(userId).getDepartment();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Page<User> getPageOfUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public String getInitials(User user) {
        return user.getLastName() +
                " " +
                user.getFirstName().toUpperCase().charAt(0) +
                ". " +
                (Objects.isNull(user.getMiddleName()) ? "" : user.getMiddleName().toUpperCase().charAt(0) + ".");
    }

    @Override
    public boolean isActive(User user) {
        return user.isActive();
    }

    @Override
    public User findOneById(int id) {
        return userRepository.findOneById(id);
    }

    // TODO: вынести дефолтный пароль в application.properties, подставлять его, если у пользователя нет пароля
    public User saveOrUpdate(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null || !user.isActive()) {
            throw new UsernameNotFoundException("Invalid username or password");
        } else {
            return new org.springframework.security.core.userdetails
                    .User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getBusinessKey())).collect(Collectors.toList());
    }
}
