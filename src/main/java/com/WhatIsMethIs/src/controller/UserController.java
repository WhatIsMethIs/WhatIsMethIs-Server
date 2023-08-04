package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.user.*;
import com.WhatIsMethIs.src.entity.User;
import com.WhatIsMethIs.src.service.SocialLoginService;
import com.WhatIsMethIs.src.service.UserService;
import com.WhatIsMethIs.utils.TokenUtils;
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
    private final TokenUtils tokenUtils;


    public UserController(UserService userService, SocialLoginService socialLoginService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.socialLoginService = socialLoginService;
        this.tokenUtils = tokenUtils;
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
    public BaseResponse<PostRes> createUser(@Parameter @RequestBody PostReq postReq) {
        try {
            PostRes postRes = userService.createUser(postReq);
            return new BaseResponse<>(postRes);
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
    public BaseResponse<PostRes> login(@Parameter(required = true, name="loginCode") @RequestParam String loginCode, @Parameter @RequestBody(required = false) PostLoginReq postLoginReq) {
        try {
            PostRes postRes;
            if (loginCode == null) {
                throw new BaseException(INVALID_REQ_PARAM);
            } else if (loginCode.equals("email")) {
                // 기본 로그인
                postRes = userService.emailLogin(postLoginReq);
            } else if(loginCode.equals("kakao")) {
                // 카카오 로그인
                String accessToken = tokenUtils.getAccessToken();
                postRes = userService.kakaoLogin(accessToken);
            } else if(loginCode.equals("apple")) {
                // 애플 로그인
                String accessToken = tokenUtils.getAccessToken();
                postRes = userService.appleLogin(accessToken);
            } else {
                throw new BaseException(INVALID_REQ_PARAM);
            }
            if (postRes.getId()<0) {
                return new BaseResponse<>(postRes);
            }
            return new BaseResponse<>(postRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 1.2.3 Refresh Token으로 자동로그인
     * [POST] /users/refreshToken
     */
    @Operation(method = "POST",
            description = "Header-'X-ACCESS-TOKEN'에 refreshToken 값을 넣어 자동 로그인 api(jwt와 refresh token 모두 새로 발급)",
            tags = "USER", summary = "Refresh Token 로그인 API - \uD83D\uDD12 refreshToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요."),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(responseCode = "4001", description = "데이터가 존재하지 않습니다.")
    })
    @ResponseBody
    @PostMapping("/refreshToken")
    public BaseResponse<PostRes> loginRefreshToken() throws BaseException {
        try {
            String accessToken = tokenUtils.getJwt();
            int index = tokenUtils.getUserId();
            PostRes postRes = userService.loginRefreshToken(index, accessToken);
            return new BaseResponse<>(postRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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
    public BaseResponse<PatchRes> modifyUser(@Parameter(required = true) @RequestBody PatchReq patchReq) {
        try {
            int userIdByJwt = tokenUtils.getUserId();
            PatchRes patchRes = userService.modifyUser(userIdByJwt, patchReq);
            return new BaseResponse<>(patchRes);

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
    @PatchMapping("/emergency")
    public BaseResponse<PatchRes> modifyEmergency(@Parameter(required = true) @RequestBody PatchEmergencyReq patchEmergencyReq) {
        try {
            int userIdByJwt = tokenUtils.getUserId();
            userService.modifyEmergencyContact(userIdByJwt, patchEmergencyReq);
            return new BaseResponse<>(new PatchRes(userIdByJwt));

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 1.3.3 Device token 저장
     * [PATCH] /users/deviceToken
     */

    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 device token 저장 api",
            tags = "USER", summary = "1.3.3 Device Token 저장 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요."),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(responseCode = "4001", description = "데이터가 존재하지 않습니다.")
    })
    @ResponseBody
    @PatchMapping("/deviceToken")
    public BaseResponse<PatchRes> saveDeviceToken(@Parameter @RequestBody PatchDeviceTokenReq patchDeviceTokenReq) throws BaseException {
        try {
            int userIdByJwt= tokenUtils.getUserId();
            PatchRes patchRes = userService.saveDeviceToken(userIdByJwt, patchDeviceTokenReq.getDeviceToken());
            return new BaseResponse<>(patchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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
    public BaseResponse<PatchRes> deleteUser() {
        try {
            int userIdByJwt = tokenUtils.getUserId();
            userService.deleteUser(userIdByJwt);
            return new BaseResponse<>(new PatchRes(userIdByJwt));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
