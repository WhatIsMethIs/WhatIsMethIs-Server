package com.WhatIsMethIs.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginReq {
    private String email;
    private String password;
}
