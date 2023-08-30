package com.WhatIsMethIs.utils;

import com.WhatIsMethIs.src.entity.MedicationConstant.*;

import java.util.Comparator;

public class TakeMealTimeComparator implements Comparator<TakeMealTime> {
    @Override
    public int compare(TakeMealTime mealTime1, TakeMealTime mealTime2) {
        if (mealTime1 == mealTime2) {
            return 0;
        } else if (mealTime1 == TakeMealTime.BREAKFAST) {
            return -1;
        } else if (mealTime2 == TakeMealTime.BREAKFAST) {
            return 1;
        } else if (mealTime2 == TakeMealTime.DINNER) {
            return -1;
        } else {
            return 1;
        }
    }
}
