package com.WhatIsMethIs.src.entity;

import com.WhatIsMethIs.src.dto.user.PatchEmergencyReq;
import com.WhatIsMethIs.src.dto.user.PatchUserInfoReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
@Schema(description = "USER 객체")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(length = 100, nullable = false)
    public String email;
    @Column(length = 200)
    @Schema(example = "null")
    public String password;
    @Column(length = 10, nullable = false)
    public String name;
    @Column(length = 4, nullable = false)
    public String age;
    @Column(length = 20, nullable = false)
    public String phoneNumber;
    @Column(length = 6, nullable = false)
    public String loginCode;
    @Column(length = 20)
    @Schema(example = "null")
    public String emergencyContact1;
    @Column(length = 20)
    @Schema(example = "null")
    public String emergencyContact2;
    @Column
    @Schema(example = "null")
    public String emergencyContact3;
    @Column
    @Schema(example = "null")
    public String refreshToken;
    @Column
    @Schema(example = "null")
    public String deviceToken;

    @Builder
    public User(int id, String email, String password, String name, String age, String phoneNumber, String loginCode, String emergencyContact1, String emergencyContact2, String emergencyContact3, String refreshToken, String deviceToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.loginCode = loginCode;
        this.emergencyContact1 = emergencyContact1;
        this.emergencyContact2 = emergencyContact2;
        this.emergencyContact3 = emergencyContact3;
        this.refreshToken = refreshToken;
        this.deviceToken = deviceToken;
    }

    public void modifyEmergencyContact(PatchEmergencyReq patchEmergencyReq) {
        this.emergencyContact1 = patchEmergencyReq.getContact1();
        this.emergencyContact2 = patchEmergencyReq.getContact2();
        this.emergencyContact3 = patchEmergencyReq.getContact3();
    }
    public void modify(PatchUserInfoReq patchUserInfoReq) {
        this.email = patchUserInfoReq.getEmail();
        this.name = patchUserInfoReq.getName();
        this.age = patchUserInfoReq.getAge();
        this.phoneNumber = patchUserInfoReq.getPhoneNumber();
    }
    public void saveDeviceToken(String str){
        this.deviceToken = str;
    }
    public void saveRefreshToken(String str){
        this.refreshToken = str;
    }
    public void modifyPassword(String str){
        this.password = password;
    }

}
