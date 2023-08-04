package com.WhatIsMethIs.src.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor
public class PatchReq {
    private String email;
    private String name;
    private String age;
    private String phoneNumber;
}
