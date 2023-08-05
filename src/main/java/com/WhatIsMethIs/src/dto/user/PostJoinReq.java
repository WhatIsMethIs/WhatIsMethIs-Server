package com.WhatIsMethIs.src.dto.user;

import com.WhatIsMethIs.src.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema
public class PostJoinReq {
    private String email;
    @Schema(example = "null")
    private String password;
    private String name;
    private String age;
    private String phoneNumber;
    @Schema(example = "email/kakao/apple, 대소문자 상관 X")
    private String loginCode;

    @Builder
    public PostJoinReq(String email, String password, String name, String age, String phoneNumber){
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .age(age)
                .phoneNumber(phoneNumber)
                .loginCode(loginCode)
                .build();
    }
}
