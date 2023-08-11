package com.WhatIsMethIs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "medication")
@DynamicInsert
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_id")
    private Medicine medicineId; //등록되지 않은 약품일 경우 0 -> medicineId가 0인 약품 미리 만들어 놔야...

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @Column(name = "medicine_image", nullable = true)
    private String medicineImage;

    @Column(name = "take_capacity", nullable = false)
    private int takeCapacity;

    @Column(name = "take_start_date", nullable = false)
    private LocalDate takeStartDate;

    @Column(name = "take_end_date", nullable = false)
    private LocalDate takeEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "take_meal_time", nullable = false)
    private MedicationConstant.TakeMealTime takeMealTime; //아침, 점심, 저녁

    @Enumerated(EnumType.STRING)
    @Column(name = "take_before_after", nullable = false)
    private MedicationConstant.TakeBeforeAfter takeBeforeAfter; //식전, 식후

    @Column(name = "take_cycle", nullable = false)
    private int takeCycle; // 1달: -1, 30일: +30

    @Column(name = "notification_time", nullable = true)
    private LocalTime notificationTime;

    @Column(name = "description", nullable = false)
    private String description;


    @Builder
    public Medication(User userId, Medicine medicineId, String medicineName, String medicineImage,
                      int takeCapacity, LocalDate takeStartDate, LocalDate takeEndDate, MedicationConstant.TakeMealTime takeMealTime,
                      MedicationConstant.TakeBeforeAfter takeBeforeAfter, int takeCycle, LocalTime notificationTime, String description) {
        this.userId = userId;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineImage = medicineImage;
        this.takeCapacity = takeCapacity;
        this.takeStartDate = takeStartDate;
        this.takeEndDate = takeEndDate;
        this.takeMealTime = takeMealTime;
        this.takeBeforeAfter = takeBeforeAfter;
        this.takeCycle = takeCycle;
        this.notificationTime = notificationTime;
        this.description = description;
    }
}
