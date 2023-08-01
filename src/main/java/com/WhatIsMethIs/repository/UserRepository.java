package com.WhatIsMethIs.repository;
import com.WhatIsMethIs.entity.User;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    Optional<User> findById(int index);
    Optional<User> findByEmail(String email);
}
