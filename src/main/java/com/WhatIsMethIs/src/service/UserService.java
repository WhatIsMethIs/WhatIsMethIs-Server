package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.config.secret.Secret;
import com.WhatIsMethIs.src.dto.user.*;
import com.WhatIsMethIs.src.entity.User;
import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.src.repository.UserRepository;
import com.WhatIsMethIs.utils.AES128;
import com.WhatIsMethIs.utils.TokenUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.WhatIsMethIs.config.BaseResponseStatus.*;
import static com.WhatIsMethIs.utils.ValidationRegex.*;

@Service
public class UserService {
    private final TokenUtils tokenUtils;
    private final SocialLoginService socialLoginService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    public UserService(TokenUtils tokenUtils, SocialLoginService socialLoginService) {
        this.tokenUtils = tokenUtils;
        this.socialLoginService = socialLoginService;
    }

    // User 전체 조회
    public List<User> getUsers() throws BaseException {
        try {
            return userRepository.findAll();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // index로 User 1명 조회
    public User getUser(int index) throws BaseException{
        User user = userRepository.findById(index).orElse(null);
        try {
            if(user == null) throw new BaseException(USER_NOT_EXIST);
            return user;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원가입
    @Transactional
    public PostRes createUser(PostJoinReq postJoinReq) throws BaseException {
        if(!isRegexEmail(postJoinReq.getEmail())){
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        }else if(!isRegexPhoneNumber(postJoinReq.getPhoneNumber())){
            throw new BaseException(POST_USERS_INVALID_PHONENUMBER);
        }else {
            User emailCheck = userRepository.findByEmail(postJoinReq.getEmail()).orElse(null);
            if (emailCheck == null){
                try {
                    String password;
                    password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postJoinReq.getPassword());
                    postJoinReq.setPassword(password);
                } catch (Exception ignored) {
                    throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
                }
                try {
                    String loginCode = postJoinReq.getLoginCode();
                    postJoinReq.setLoginCode(loginCode.toLowerCase());
                    User user = postJoinReq.toEntity();
                    userRepository.save(user);
                    String refreshToken = tokenUtils.createRefreshToken(user.getId());
                    user.saveRefreshToken(refreshToken);
                    return new PostRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()), refreshToken);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    throw new BaseException(DATABASE_ERROR);
                }
            } else {
                throw new BaseException(EXIST_EMAIL);
            }
        }
    }

    // 이메일 로그인
    @Transactional
    public PostRes emailLogin(PostLoginReq postLoginReq) throws BaseException {
        String email = postLoginReq.getEmail();
        if (email == null || email.isEmpty()) {
            throw new BaseException(POST_USERS_EMPTY_EMAIL);
        }
        else if (!isRegexEmail(email)) {
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        }
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null){
            String password;
            try {
                password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
            } catch (Exception ignored) {
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }
            if (postLoginReq.getPassword().equals(password)) {
                String refreshToken = tokenUtils.createRefreshToken(user.getId());
                user.saveRefreshToken(refreshToken);
                return new PostRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()), refreshToken);
            }
            else {
                throw new BaseException(FAIL_TO_LOGIN);
            }
        } else {
            throw new BaseException(FAIL_TO_LOGIN);
        }
    }

    // 카카오 로그인
    @Transactional
    public PostRes kakaoLogin(String accessToken) throws BaseException {
        User user = socialLoginService.getKakaoUserByAccessToken(accessToken);
        if (user != null) {
            if(user.getLoginCode().equalsIgnoreCase("email")){
                throw new BaseException(LOGIN_TO_EMAIL);
            }
            String refreshToken = tokenUtils.createRefreshToken(user.getId());
            user.saveRefreshToken(refreshToken);
            return new PostRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()), refreshToken);
        } else {
            return new PostRes(-1, "", "", "");
        }
    }

    // 애플 로그인
    @Transactional
    public PostRes appleLogin(String accessToken) throws BaseException {
        User user = socialLoginService.getAppleUserByAccessToken(accessToken);
        if (user != null) {
            if(user.getLoginCode().equalsIgnoreCase("email")){
                throw new BaseException(LOGIN_TO_EMAIL);
            }
            String refreshToken = tokenUtils.createRefreshToken(user.getId());
            user.saveRefreshToken(refreshToken);
            return new PostRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()), refreshToken);
        } else {
            return new PostRes(-1, "", "", "");
        }
    }

    // 자동로그인
    @Transactional
    public PostRes loginRefreshToken(int index, String accessToken) throws BaseException {
        try {
            User user = userRepository.findById(index).orElse(null);
            if (user != null && user.getId() == index) {
                if (user.getRefreshToken().equals(accessToken)){
                    // refresh token 유효 -> jwt 재발급
                    String jwt = tokenUtils.createJwt(index);
                    String refreshToken = tokenUtils.createRefreshToken(index);
                    user.saveRefreshToken(refreshToken);
                    return new PostRes(index, user.getEmail(), jwt, refreshToken);
                } else {
                    throw new BaseException(INVALID_JWT);
                }
            } else {
                throw new BaseException(USER_NOT_EXIST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // User 수정
    @Transactional
    public PatchRes modifyUser(int index, PatchUserInfoReq patchUserInfoReq) throws BaseException {
        if(!isRegexEmail(patchUserInfoReq.getEmail())){
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        }else if(!isRegexPhoneNumber(patchUserInfoReq.getPhoneNumber())){
            throw new BaseException(POST_USERS_INVALID_PHONENUMBER);
        } else {
            try {
                User emailCheck = userRepository.findByEmail(patchUserInfoReq.getEmail()).orElse(null);
                if(emailCheck == null || emailCheck.getId() == index){
                    User user = userRepository.findById(index).orElse(null);
                    if (user != null && user.getId() == index) {
                        user.modify(patchUserInfoReq);
                        return new PatchRes(user.getId());
                    } else {
                        throw new BaseException(USER_NOT_EXIST);
                    }
                } else { // 다른 사용자가 이미 사용 중인 이메일일 경우
                    throw new BaseException(EXIST_EMAIL);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new BaseException(DATABASE_ERROR);
            }

        }
    }
    // 비상연락망 등록 및 수정
    @Transactional
    public PatchRes modifyEmergencyContact(int index, PatchEmergencyReq modifyReq) throws BaseException {
        if(!isRegexPhoneNumber(modifyReq.getContact1()) || !isRegexPhoneNumber(modifyReq.getContact2()) || !isRegexPhoneNumber(modifyReq.getContact3())){
            throw new BaseException(POST_USERS_INVALID_PHONENUMBER);
        }else {
            try {
                User user = userRepository.findById(index).orElse(null);
                if (user != null) {
                    user.modifyEmergencyContact(modifyReq);
                    return new PatchRes(user.getId());
                } else {
                    throw new BaseException(USER_NOT_EXIST);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }
    // device token 저장
    @Transactional
    public PatchRes saveDeviceToken(int index, String str) throws  BaseException {
        try {
            User user = userRepository.findById(index).orElse(null);
            if (user != null && user.getId() == index) {
                user.saveDeviceToken(str);
                return new PatchRes(user.getId());
            } else {
                throw new BaseException(USER_NOT_EXIST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 비밀번호 수정
    @Transactional
    public PatchRes modifyPassword(int index, PatchPasswordReq patchPasswordReq) throws BaseException {
        try {
            User user = userRepository.findById(index).orElse(null);
            if (user != null && user.getId() == index) {
                String originPwd;
                String newPwd;
                try {
                    originPwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
                } catch (Exception ignored) {
                    throw new BaseException(PASSWORD_DECRYPTION_ERROR);
                }
                try {
                    newPwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchPasswordReq.getNewPwd());
                } catch (Exception ignored) {
                    throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
                }
                if (patchPasswordReq.getOriginPwd().equals(originPwd)) {
                    user.modifyPassword(newPwd);
                    return new PatchRes(user.getId());
                } else {
                    throw new BaseException(WRONG_PASSWORD);
                }
            } else {
                throw new BaseException(USER_NOT_EXIST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 로그아웃
    @Transactional
    public void logout(int index) throws BaseException {
        try {
            User user = userRepository.findById(index).orElse(null);
            if(user != null && user.getId() == index){
                user.saveDeviceToken("");
                user.saveRefreshToken("");
            } else {
                throw new BaseException(USER_NOT_EXIST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // User 삭제
    @Transactional
    public void deleteUser(int userId) throws BaseException {
        userRepository.deleteById(userId);
    }
}
