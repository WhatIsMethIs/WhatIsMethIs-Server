package com.WhatIsMethIs.src.repository;

import com.WhatIsMethIs.src.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    List<Medicine> findAll();
}
