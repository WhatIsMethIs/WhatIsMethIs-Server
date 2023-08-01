package com.WhatIsMethIs.controller;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.dto.user.*;
import com.WhatIsMethIs.entity.User;
import com.WhatIsMethIs.service.SocialLoginService;
import com.WhatIsMethIs.service.UserService;
import com.WhatIsMethIs.service.TokenService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.WhatIsMethIs.config.BaseResponseStatus.INVALID_REQ_PARAM;

@RestController
@RequestMapping("/app/users")
@Tag(name = "USER", description = "유저 API")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final SocialLoginService socialLoginService;
    @Autowired
    private final TokenService tokenService;


    public UserController(UserService userService, SocialLoginService socialLoginService, TokenService tokenService) {
        this.userService = userService;
        this.socialLoginService = socialLoginService;
        this.tokenService = tokenService;
    }


    /**
     * 1.1.1 유저 전체 조회
     * [GET] /users
     */
    @Operation(method = "GET",
            description = "유저 전체 조회 api", tags = "USER", summary = "1.1.1 유저 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.")
    })
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<User>> getUsers() {
        try {
            List<User> usersList = userService.getUsers();
            return new BaseResponse<>(usersList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 1.1.2 유저 1명 조회
     * [GET] /users/:index
     */
    @Operation(method = "GET",
            description = "유저 1명 조회 api", tags = "USER", summary = "1.1.2 유저 index로 유저 1명 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.")
    })
    @ResponseBody
    @GetMapping("/{index}")
    public BaseResponse<User> getUser(@PathVariable("index") int index) {
        try {
            User user = userService.getUser(index);
            return new BaseResponse<>(user);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 1.2.1 회원가입
     * [POST] /users
     */
    @Operation(method = "POST",
            description = "이름, 이메일, 비밀번호, 나이, 휴대폰번호로 회원가입 api", tags = "USER", summary = "1.2.1 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.")
    })
    @ResponseBody
    @PostMapping("")
    public BaseResponse<UserRes> createUser(@Parameter @RequestBody UserReq postUserReq) {
        try {
            UserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 1.2.2 로그인(이메일/카카오/로그인)
     * [POST] /users/login?code={loginCode}
     */
    @Operation(method = "POST",
            description = "loginCode(email/kakao/apple)에 따라 로그인 api", tags = "USER", summary = "1.2.2 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2010", description = "이메일을 입력해주세요."),
            @ApiResponse(responseCode = "2011", description = "이메일 형식을 확인해주세요."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(responseCode = "4001", description = "데이터가 존재하지 않습니다.")
    })
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<UserRes> login(@Parameter(required = true) @RequestParam String loginCode, @Parameter @RequestBody(required = false) LoginReq loginReq) {
        try {
            UserRes userRes;
            if (loginCode == null) {
                throw new BaseException(INVALID_REQ_PARAM);
            } else if (loginCode.equals("email")) {
                // 기본 로그인
                userRes = userService.emailLogin(loginReq);
            } else if(loginCode.equals("kakao")) {
                // 카카오 로그인
                String accessToken = tokenService.getAccessToken();
                userRes = userService.kakaoLogin(accessToken);
            } else if(loginCode.equals("apple")) {
                // 애플 로그인
                String accessToken = tokenService.getAccessToken();
                userRes = userService.appleLogin(accessToken);
            } else {
                throw new BaseException(INVALID_REQ_PARAM);
            }
            if (userRes.getId()<0) {
                return new BaseResponse<>(userRes);
            }
            return new BaseResponse<>(userRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 1.3.1 유저 정보 수정
     * [PATCH] /users
     */
    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 유저 정보(이메일, 이름, 나이, 전화번호) 수정 api",
            tags = "USER", summary = "1.3.1 유저 정보 수정 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요."),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.")
    })
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<ModifyRes> modifyUser(@Parameter(required = true) @RequestBody ModifyReq modifyReq) {
        try {
            int userIdByJwt = tokenService.getUserId();
            ModifyRes modifyRes = userService.modifyUser(userIdByJwt, modifyReq);
            return new BaseResponse<>(modifyRes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 1.3.2 비상연락망 등록 및 수정
     * [PATCH] /users
     */
    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 비상연락망 등록 및 수정 api",
            tags = "USER", summary = "1.3.2 비상연락망 등록 및 수정 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요."),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.")
    })
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<PatchUserRes> modifyEmergency(@Parameter(required = true) @RequestBody ModifyEmergencyReq modifyEmergencyReq) {
        try {
            int userIdByJwt = tokenService.getUserId();
            userService.modifyEmergencyContact(userIdByJwt, modifyEmergencyReq);
            return new BaseResponse<>(new PatchUserRes(userIdByJwt));

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 1.4.1 유저 삭제
     * [DELETE] /users
     */
    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 유저 삭제 api",
            tags = "USER", summary = "1.4.1 유저 삭제 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요."),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.")
    })
    @ResponseBody
    @DeleteMapping("")
    public BaseResponse<PatchUserRes> deleteUser() {
        try {
            int userIdByJwt = tokenService.getUserId();
            userService.deleteUser(userIdByJwt);
            return new BaseResponse<>(new PatchUserRes(userIdByJwt));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
