package com.WhatIsMethIs.config.exception.medication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MedicationExceptionList {
    NOT_FOUND_MEDICATION_ID_ERROR("C0001", HttpStatus.NOT_FOUND, "해당 medicationId로 Medication을 찾을 수 없습니다."),
    NOT_MY_MEDICATION_ERROR("C0002", HttpStatus.FORBIDDEN, "해당 회원의 복약 정보가 아닙니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
