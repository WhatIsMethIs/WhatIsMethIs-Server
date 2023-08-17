package com.WhatIsMethIs.src.repository;

import com.WhatIsMethIs.src.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    List<Medicine> findAll();

    Optional<Medicine> findById(String id);
}
