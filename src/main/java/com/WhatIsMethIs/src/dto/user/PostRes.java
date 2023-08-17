package com.WhatIsMethIs.src.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema
public class PostRes {
    private int id;
    private String email;
    private String jwt;
    private String refreshToken;
}
