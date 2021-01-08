package com.documentflow.services;

import com.documentflow.entities.Department;
import com.documentflow.entities.Role;
import com.documentflow.entities.User;
import com.documentflow.exceptions.UserNotActiveException;
import com.documentflow.repositories.UserRepository;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
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
import java.util.stream.Collectors;

@Service
@Transactional
@Setter(onMethod_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean isExistsUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public User saveOrUpdate(User user) {
        if(user.getId() == null ){
            if (userRepository.existsUserByUsername(user.getUsername())){
                throw new IllegalArgumentException("Попытка создания не уникального логина");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            //поле логин наверн лучше сделать уникальным, чтоб не страдать
            User currentUser = userRepository.findUserByUsername(user.getUsername());
            User oldUser = userRepository.findOneById(user.getId());
            if(currentUser.getId() != user.getId()){
                user.setUsername(oldUser.getUsername());
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(int userId) {
        return userRepository.findOneById(userId);
    }

    public void delete(User user) {
        userRepository.delete(user);
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
                (StringUtils.isEmpty(user.getMiddleName()) ? "" : user.getMiddleName().toUpperCase().charAt(0) + ".");
    }

    @Override
    public boolean isActive(User user) {
        return user.isActive();
    }

    @Override
    public User findOneById(int id) {
        return userRepository.findOneById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        checkUser(user);
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    private void checkUser(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        if (!user.isActive()) {
            throw new UserNotActiveException("user isn't active");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getBusinessKey())).collect(Collectors.toList());
    }
}
