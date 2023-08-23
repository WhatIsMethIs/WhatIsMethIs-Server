package com.WhatIsMethIs.config.exception.user;

public class NotFoundUserIdException extends UserException{
    public NotFoundUserIdException(){
        super(UserExceptionList.NOT_FOUND_USER_ID_ERROR.getErrorCode(), UserExceptionList.NOT_FOUND_USER_ID_ERROR.getHttpStatus(), UserExceptionList.NOT_FOUND_USER_ID_ERROR.getMessage());
    }
}
