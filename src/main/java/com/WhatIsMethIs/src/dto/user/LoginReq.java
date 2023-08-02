package com.WhatIsMethIs.src.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginReq {
    private String email;
    private String password;
}
