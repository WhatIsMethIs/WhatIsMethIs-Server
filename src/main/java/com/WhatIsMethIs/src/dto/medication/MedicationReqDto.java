package com.WhatIsMethIs.src.dto.medication;

import com.WhatIsMethIs.src.entity.MedicationConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
public class MedicationReqDto {
    @Getter
    public static class MedicationInfoReq{
        private String medicineId; //등록되지 않은 약품일 경우 0 -> medicineId가 0인 약품 미리 만들어 놔야...
        private String medicineName;
        private String medicineImage;
        private int takeCapacity;
        private LocalDate takeStartDate;
        private LocalDate takeEndDate;
        private MedicationConstant.TakeMealTime takeMealTime; //아침, 점심, 저녁
        private MedicationConstant.TakeBeforeAfter takeBeforeAfter; //식전, 식후
        private int takeCycle; // 1달: -1, 30일: +30
        private LocalTime notificationTime;
        private String description;
    }
}
