package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.user.*;
import com.WhatIsMethIs.src.entity.User;
import com.WhatIsMethIs.src.service.UserService;
import com.WhatIsMethIs.utils.TokenUtils;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
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
    private final TokenUtils tokenUtils;


    public UserController(UserService userService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }


    /**
     * 1.1.1 유저 전체 조회
     * [GET] /users
     */
    @Operation(method = "GET",
            description = "유저 전체를 조회하는 api로, USER 객체 그대로 반환합니다.", tags = "USER", summary = "1.1.1 유저 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
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
            description = "유저 1명을 조회하는 api로, USER 객체 그대로 반환합니다. 단, index 값에 해당하는 유저가 없을 경우 에러 반환(code=3005) " +
                    "* nullable : password, emerge~, refreshToken, deviceToken", tags = "USER", summary = "1.1.2 유저 index로 유저 1명 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
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
     * 1.1.3 전화번호로 유저 1명 조회
     * [GET] /users/phoneNumber?value={phoneNumber}
     */
    @Operation(method = "GET",
            description = "전화번호로 유저 1명을 조회하는 api로, USER 객체 그대로 반환합니다. 단, 해당하는 유저가 없을 경우 에러 반환(code=3005) " +
                    "* nullable : password, emerge~, refreshToken, deviceToken", tags = "USER", summary = "1.1.3 전화번호로 유저 1명 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
    })
    @ResponseBody
    @GetMapping("/phoneNumber")
    public BaseResponse<User> getUserByPhoneNumber(@Parameter(required = true, name="phoneNumber", example = "000-0000-0000") @RequestParam String phoneNumber) {
        try {
            User user = userService.getUserByPhoneNumber(phoneNumber);
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
            description = "이름, 이메일, (비밀번호), 나이, 휴대폰번호로 회원가입하는 api이며, 소셜로그인인 경우 비밀번호는 공란으로 넣어주시면 됩니다. ",
            tags = "USER", summary = "1.2.1 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2011", description = "이메일 형식을 확인해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2012", description = "전화번호 형식을 확인해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2013", description = "이미 가입한 이메일입니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "4010", description = "비밀번호 암호화에 실패하였습니다.", content = @Content())

    })
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostRes> createUser(@Parameter @RequestBody PostJoinReq postJoinReq) {
        try {
            PostRes postRes = userService.createUser(postJoinReq);
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
            description = "loginCode에 따라 로그인하는 api이며, 소셜로그인 선택한 유저 중 회원이 아닐 경우 id = -1로 return합니다. (즉, id = -1일 경우 회원가입 절차 밟아야 함)", tags = "USER", summary = "1.2.2 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2010", description = "이메일을 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2011", description = "이메일 형식을 확인해주세요.", content = @Content()),
            @ApiResponse(responseCode = "3000", description = "없는 이메일이거나 비밀번호가 틀렸습니다.", content = @Content()),
            @ApiResponse(responseCode = "3001", description = "이메일 정보를 가져오는데 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "3002", description = "카카오 api 호출 응답 정보를 불러오는데 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "3003", description = "애플 api 호출 응답 정보를 불러오는데 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "3004", description = "애플 api 호출 중 사용 가능한 공개키가 없어 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "3006", description = "이메일 가입 사용자입니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "4011", description = "비밀번호 복호화에 실패하였습니다.", content = @Content())
    })
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostRes> login(@Parameter(required = true, name="loginCode", example = "email/kakao/apple") @RequestParam String loginCode, @Parameter @RequestBody(required = false) PostLoginReq postLoginReq) {
        try {
            PostRes postRes;
            if (loginCode == null) {
                throw new BaseException(INVALID_REQ_PARAM);
            } else if (loginCode.equalsIgnoreCase("email")) {
                // 기본 로그인
                postRes = userService.emailLogin(postLoginReq);
            } else if(loginCode.equalsIgnoreCase("kakao")) {
                // 카카오 로그인
                String accessToken = tokenUtils.getAccessToken();
                postRes = userService.kakaoLogin(accessToken);
            } else if(loginCode.equalsIgnoreCase("apple")) {
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
            description = "Header-'X-ACCESS-TOKEN'에 refreshToken 값을 넣어 자동 로그인하는 api이며, jwt와 refresh token 모두 새로 발급합니다.",
            tags = "USER", summary = "1.2.3 Refresh Token 로그인 - \uD83D\uDD12 refreshToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
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
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 유저 정보(이메일, 이름, 나이, 전화번호)를 수정하는 api입니다.",
            tags = "USER", summary = "1.3.1 유저 정보 수정 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "2011", description = "이메일 형식을 확인해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2012", description = "전화번호 형식을 확인해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2013", description = "이미 가입한 이메일입니다.", content = @Content()),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
    })
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<PatchRes> modifyUser(@Parameter(required = true) @RequestBody PatchUserInfoReq patchUserInfoReq) {
        try {
            int userIdByJwt = tokenUtils.getUserId();
            PatchRes patchRes = userService.modifyUser(userIdByJwt, patchUserInfoReq);
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
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 비상연락망을 등록 및 수정하는 api입니다.",
            tags = "USER", summary = "1.3.2 비상연락망 등록 및 수정 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "2012", description = "전화번호 형식을 확인해주세요.", content = @Content()),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
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
     * 1.3.3 Device token 등록 및 수정
     * [PATCH] /users/deviceToken
     */

    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 device token을 등록 및 수정하는 api입니다.",
            tags = "USER", summary = "1.3.3 Device Token 저장 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content()),
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
     * 1.3.4 비밀번호 수정
     * [PATCH] /users/password
     */
    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 비밀번호를 수정하는 api입니다. originPwd가 맞는지 확인 후 업데이트합니다.",
            tags = "USER", summary = "1.3.4 비밀번호 수정 - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "3007", description = "비밀번호가 틀렸습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "4010", description = "비밀번호 암호화에 실패하였습니다.", content = @Content()),
            @ApiResponse(responseCode = "4011", description = "비밀번호 복호화에 실패하였습니다.", content = @Content())
    })
    @ResponseBody
    @PatchMapping("/password")
    public BaseResponse<PatchRes> modifyPassword(@Parameter @RequestBody PatchPasswordReq patchPasswordReq) throws BaseException {
        try {
            int userIdByJwt= tokenUtils.getUserId();
            PatchRes patchRes = userService.modifyPassword(userIdByJwt, patchPasswordReq);
            return new BaseResponse<>(patchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 1.3.5 로그아웃
     * [PATCH] /users/logout
     */
    @Operation(method = "PATCH",
            description = "Header-'X-ACCESS-TOKEN'에 JWT 값을 넣어 로그아웃 처리하는 api입니다.",
            tags = "USER", summary = "1.3.5 로그아웃 API - \uD83D\uDD12 JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "3005", description = "유저가 존재하지 않습니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content()),
    })
    @ResponseBody
    @PatchMapping("/logout")
    public BaseResponse<PatchRes> logout() {
        try {
            int userIdByJwt = tokenUtils.getUserId();
            userService.logout(userIdByJwt);
            return new BaseResponse<>(new PatchRes(userIdByJwt));
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
            @ApiResponse(responseCode = "2000", description = "JWT를 입력해주세요.", content = @Content()),
            @ApiResponse(responseCode = "2001", description = "유효하지 않은 JWT입니다.", content = @Content()),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content())
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
