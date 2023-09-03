package com.WhatIsMethIs.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    EMPTY_JWT(false, 2000, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2001, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2002,"권한이 없는 유저의 접근입니다."),
    EMPTY_ACCESS_TOKEN(false, 2003, "accessToken을 입력해주세요."),
    INVALID_REQ_PARAM(false,2004,"파라미터 값을 확인해주세요."),
    POST_USERS_EMPTY_EMAIL(false, 2010, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2011, "이메일 형식을 확인해주세요."),
    POST_USERS_INVALID_PHONENUMBER(false, 2012, "전화번호 형식을 확인해주세요."),
    EXIST_EMAIL(false,2013,"이미 가입한 이메일입니다."),

    FILE_NOT_FOUND_EXCEPTION(false, 2014, "파일이 없습니다."),
    FILEHANDLER_FUNC_TRANFER_TO_EXCEPTION(false, 2015, "class FileHandler에서 transferTo 실행 중 IOException 발생"),
    INVALID_URL(false, 2016, "잘못된 URL 요청입니다."),
    TOO_MANY_FILES(false, 2017, "너무 많은 파일이 입력되었습니다."),

    /**
     * 3000 : Response 오류
     */
    // [POST] /users
    FAIL_TO_LOGIN(false, 3000, "없는 이메일이거나 비밀번호가 틀렸습니다."),
    FAIL_TO_GET_EMAIL(false, 3001, "이메일 정보를 가져오는데 실패하였습니다."),
    FAIL_TO_RESPONSE_KAKAO(false, 3002, "카카오 api 호출 응답 정보를 불러오는데 실패하였습니다."),
    FAIL_TO_RESPONSE_APPLE(false, 3003, "애플 api 호출 응답 정보를 불러오는데 실패하였습니다."),
    FAIL_TO_FIND_AVALIABLE_RSA(false,3004,"애플 api 호출 중 사용 가능한 공개키가 없어 실패하였습니다."),
    USER_NOT_EXIST(false, 3005, "유저가 존재하지 않습니다."),
    LOGIN_TO_EMAIL(false, 3006, "이메일 가입 사용자입니다."),
    WRONG_PASSWORD(false,3007,"비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 4010, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4011, "비밀번호 복호화에 실패하였습니다."),

    /**
     * 5000 : OpenApi 및 모델 서버 응답 형식 오류
     */
    RESPONSE_IS_XML(false, 5000, "Open Api 연결에 실패하셨습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
