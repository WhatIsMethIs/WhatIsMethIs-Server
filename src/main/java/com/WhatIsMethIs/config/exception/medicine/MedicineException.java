package com.WhatIsMethIs.config.exception.medicine;

import com.WhatIsMethIs.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MedicineException extends ApplicationException {
    protected MedicineException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
