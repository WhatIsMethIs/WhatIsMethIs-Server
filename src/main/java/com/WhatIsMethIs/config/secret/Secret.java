package com.WhatIsMethIs.config.secret;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Secret {

    public static String JWT_SECRET_KEY;

    public static String USER_INFO_PASSWORD_KEY;

    public Secret(@Value("${secret.jwt-secret-key}") String jwtKey, @Value("${secret.user-info-password-key}")String passwordKey) {
        JWT_SECRET_KEY = jwtKey;
        USER_INFO_PASSWORD_KEY = passwordKey;
    }
}