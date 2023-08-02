package com.WhatIsMethIs.src.dto.user;

import com.WhatIsMethIs.src.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReq {
    private String email;
    private String password;
    private String name;
    private String age;
    private String phoneNumber;

    @Builder
    public UserReq(String email, String password, String name, String age, String phoneNumber){
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
                .build();
    }
}
