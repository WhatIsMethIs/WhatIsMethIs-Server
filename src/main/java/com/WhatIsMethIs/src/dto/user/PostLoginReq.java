package com.WhatIsMethIs.src.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema
public class PostLoginReq {
    @Schema(example = "소셜로그인일 경우 공란")
    private String email;
    @Schema(example = "소셜로그인일 경우 공란")
    private String password;
}
