package com.WhatIsMethIs.config.exception.medication;

import com.WhatIsMethIs.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class MedicationException extends ApplicationException {
    protected MedicationException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}

