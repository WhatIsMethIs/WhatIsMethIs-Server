package com.WhatIsMethIs.config.exception.medication;

public class NotFoundMedicationIdException extends MedicationException{
    public NotFoundMedicationIdException(){
        super(MedicationExceptionList.NOT_FOUND_MEDICATIONID_ERROR.getErrorCode(), MedicationExceptionList.NOT_FOUND_MEDICATIONID_ERROR.getHttpStatus(), MedicationExceptionList.NOT_FOUND_MEDICATIONID_ERROR.getMessage());
    }
}
