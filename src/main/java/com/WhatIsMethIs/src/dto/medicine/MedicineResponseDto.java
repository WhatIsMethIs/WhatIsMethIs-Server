package com.WhatIsMethIs.src.dto.medicine;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MedicineResponseDto {
    private int pageNo;
    private int totalCount;
    private int numOfRows;
    private List<MedicineDto> medicines = new ArrayList<>();
}
