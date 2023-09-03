package com.WhatIsMethIs.utils;

import com.WhatIsMethIs.src.entity.Medication;

import java.util.Comparator;

public class MedicationComparator implements Comparator<Medication> {
    @Override
    public int compare(Medication med1, Medication med2) {
        int mealTimeComparison = med1.getTakeMealTime().compareTo(med2.getTakeMealTime());

        if (mealTimeComparison != 0) {
            // TakeMealTime이 다르면, TakeMealTime 비교 우선
            return mealTimeComparison;
        } else {
            // TakeMealTime이 같으면, TakeBeforeAfter 비교
            return med1.getTakeBeforeAfter().compareTo(med2.getTakeBeforeAfter());
        }
    }
}
