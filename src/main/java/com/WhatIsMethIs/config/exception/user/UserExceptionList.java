package com.WhatIsMethIs.config.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionList {
    NOT_FOUND_USER_ID_ERROR("U0001", HttpStatus.NOT_FOUND, "해당 userId로 User를 찾을 수 없습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
