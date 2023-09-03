package com.WhatIsMethIs.src.repository;
import com.WhatIsMethIs.src.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    Optional<User> findById(int index);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
}
