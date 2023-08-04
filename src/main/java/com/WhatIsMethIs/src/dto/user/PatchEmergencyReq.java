package com.WhatIsMethIs.src.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchEmergencyReq {
    private String contact1;
    private String contact2;
    private String contact3;
}
