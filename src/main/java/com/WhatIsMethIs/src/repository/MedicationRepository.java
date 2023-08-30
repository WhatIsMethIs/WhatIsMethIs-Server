package com.WhatIsMethIs.src.repository;

import com.WhatIsMethIs.src.entity.Medication;
import com.WhatIsMethIs.src.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findById(Long id);
    Page<Medication> findByUserIdOrderByIdDesc(User userId, Pageable pageable);
    List<Medication> findAllByUserId(User userId);
}
