package com.WhatIsMethIs.config.exception.user;

import com.WhatIsMethIs.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserException extends ApplicationException {
    protected UserException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
