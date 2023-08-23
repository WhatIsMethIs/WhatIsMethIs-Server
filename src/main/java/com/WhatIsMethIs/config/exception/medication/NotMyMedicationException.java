package com.WhatIsMethIs.config.exception.medication;

public class NotMyMedicationException extends MedicationException{
    public NotMyMedicationException(){
        super(MedicationExceptionList.NOT_MY_MEDICATION_ERROR.getErrorCode(), MedicationExceptionList.NOT_MY_MEDICATION_ERROR.getHttpStatus(), MedicationExceptionList.NOT_MY_MEDICATION_ERROR.getMessage());
    }
}
