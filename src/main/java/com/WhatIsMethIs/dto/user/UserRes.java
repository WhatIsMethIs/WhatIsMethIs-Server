package com.WhatIsMethIs.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRes {
    private int id;
    private String email;
    private String jwt;
}
