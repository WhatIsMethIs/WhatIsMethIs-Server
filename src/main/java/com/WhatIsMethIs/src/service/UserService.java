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

import java.beans.Transient;
import java.util.List;

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
//        this.userRepository = userRepository;
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
    public UserRes createUser(UserReq userReq) throws BaseException {

        if(!isRegexEmail(userReq.getEmail())){
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        }else if(!isRegexPhoneNumber(userReq.getPhoneNumber())){
            throw new BaseException(POST_USERS_INVALID_PHONENUMBER);
        }else {
            User emailCheck = userRepository.findByEmail(userReq.getEmail()).orElse(null);
            if (emailCheck == null){
                try {
                    String password;
                    password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(userReq.getPassword());
                    userReq.setPassword(password);
                } catch (Exception ignored) {
                    throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
                }
                try {
                    User user = userReq.toEntity();
                    userRepository.save(user);
                    return new UserRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                    throw new BaseException(DATABASE_ERROR);
                }
            } else {
                throw new BaseException(POST_USERS_EXIST_EMAIL);
            }
        }
    }

    // 이메일 로그인
    public UserRes emailLogin(LoginReq loginReq) throws BaseException {
        String email = loginReq.getEmail();
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
            if (loginReq.getPassword().equals(password)) {
                return new UserRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()));
            }
            else {
                throw new BaseException(FAIL_TO_LOGIN);
            }
        } else {
            throw new BaseException(FAIL_TO_LOGIN);
        }
    }

    // 카카오 로그인
    public UserRes kakaoLogin(String accessToken) throws BaseException {
        User user = socialLoginService.getKakaoUserByAccessToken(accessToken);
        if (user != null) {
            return new UserRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()));
        } else {
            return new UserRes(-1, "", "");
        }
    }

    // 애플 로그인
    public UserRes appleLogin(String accessToken) throws BaseException {
        User user = socialLoginService.getAppleUserByAccessToken(accessToken);
        if (user != null) {
            return new UserRes(user.getId(), user.getEmail(), tokenUtils.createJwt(user.getId()));
        } else {
            return new UserRes(-1, "", "");
        }
    }

    // User 수정 // 수정하기
    @Transactional
    public ModifyRes modifyUser(int index, ModifyReq modifyReq) throws BaseException {
        if(!isRegexEmail(modifyReq.getEmail())){
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        }else if(!isRegexPhoneNumber(modifyReq.getPhoneNumber())){
            throw new BaseException(POST_USERS_INVALID_PHONENUMBER);
        } else {
            try {

                User user = userRepository.findById(index).orElse(null);
                if (user != null && user.getId() == index) {
                    user.modify(modifyReq);
                    return new ModifyRes(user.getId());
                } else {
                    throw new BaseException(USER_NOT_EXIST);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }


    // 비상연락망 등록 및 수정
    @Transactional
    public ModifyRes modifyEmergencyContact(int index, ModifyEmergencyReq modifyReq) throws BaseException {
        if(!isRegexPhoneNumber(modifyReq.getContact1()) || !isRegexPhoneNumber(modifyReq.getContact2()) || !isRegexPhoneNumber(modifyReq.getContact3())){
            throw new BaseException(POST_USERS_INVALID_PHONENUMBER);
        }else {
            try {
                User user = userRepository.findById(index).orElse(null);
                if (user != null) {
                    user.modifyEmergencyContact(modifyReq);
                    return new ModifyRes(user.getId());
                } else {
                    throw new BaseException(USER_NOT_EXIST);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }

    // User 삭제
    @Transactional
    public void deleteUser(int userId) throws BaseException {
        userRepository.deleteById(userId);
    }
}
