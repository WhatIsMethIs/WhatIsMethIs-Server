package com.WhatIsMethIs.src.entity;

import lombok.Getter;

public class MedicationConstant {

    @Getter
    public enum TakeMealTime{
        BREAKFAST, LUNCH, DINNER
    }

    @Getter
    public enum TakeBeforeAfter{
        BEFORE, AFTER
    }
}
