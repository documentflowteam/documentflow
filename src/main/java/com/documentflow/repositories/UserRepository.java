package com.documentflow.repositories;

import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    User findOneById(int id);

    User findUserByUsername(String username);

    boolean existsUserByUsername(String username);
}
