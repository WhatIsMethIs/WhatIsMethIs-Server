package com.WhatIsMethIs.src.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchPasswordReq {
    private String originPwd;
    private String newPwd;
}
