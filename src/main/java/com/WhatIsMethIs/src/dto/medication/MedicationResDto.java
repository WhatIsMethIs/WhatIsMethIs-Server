package com.WhatIsMethIs.src.dto.medication;

import com.WhatIsMethIs.src.entity.Medication;
import com.WhatIsMethIs.src.entity.MedicationConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
public class MedicationResDto {

    @Getter
    public static class MedicationInfoRes{
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

        @Builder
        public MedicationInfoRes(Medication medication){
            this.medicineName = medication.getMedicineName();
            this.medicineImage = medication.getMedicineImage();
            this.takeCapacity = medication.getTakeCapacity();;
            this.takeStartDate = medication.getTakeStartDate();
            this.takeEndDate = medication.getTakeEndDate();
            this.takeMealTime = medication.getTakeMealTime();
            this.takeBeforeAfter = medication.getTakeBeforeAfter();
            this.takeCycle = medication.getTakeCycle();
            this.notificationTime = medication.getNotificationTime();
            this.description = medication.getDescription();

        }
    }
}