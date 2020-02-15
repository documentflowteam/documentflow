package com.documentflow.repositories;

import com.documentflow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    User findOneById(int id);

    User findUserByUsername(String username);
}
