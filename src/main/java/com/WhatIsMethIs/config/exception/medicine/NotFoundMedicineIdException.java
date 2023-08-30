package com.WhatIsMethIs.config.exception.medicine;

public class NotFoundMedicineIdException extends MedicineException{
    public NotFoundMedicineIdException(){
        super(MedicineExceptionList.NOT_FOUND_MEDICINE_ID_ERROR.getErrorCode(), MedicineExceptionList.NOT_FOUND_MEDICINE_ID_ERROR.getHttpStatus(), MedicineExceptionList.NOT_FOUND_MEDICINE_ID_ERROR.getMessage());
    }
}
