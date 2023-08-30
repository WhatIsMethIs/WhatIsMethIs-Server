package com.WhatIsMethIs.config.exception.medicine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MedicineExceptionList {
    NOT_FOUND_MEDICINE_ID_ERROR("M0001", HttpStatus.NOT_FOUND, "해당 id로 Medicine을 찾을 수 없습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
