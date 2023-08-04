package com.WhatIsMethIs.src.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRes {
    private int id;
    private String email;
    private String jwt;
    private String refreshToken;
}
