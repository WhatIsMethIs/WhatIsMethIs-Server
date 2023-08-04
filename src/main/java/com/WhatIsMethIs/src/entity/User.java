package com.WhatIsMethIs.src.entity;

import com.WhatIsMethIs.src.dto.user.PatchEmergencyReq;
import com.WhatIsMethIs.src.dto.user.PatchReq;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(length = 100, nullable = false)
    public String email;
    @Column(length = 200)
    public String password;
    @Column(length = 10, nullable = false)
    public String name;
    @Column(length = 4, nullable = false)
    public String age;
    @Column(length = 20, nullable = false)
    public String phoneNumber;
    @Column(length = 20)
    public String emergencyContact1;
    @Column(length = 20)
    public String emergencyContact2;
    @Column
    public String emergencyContact3;
    @Column
    public String refreshToken;
    @Column
    public String deviceToken;

    @Builder
    public User(int id, String email, String password, String name, String age, String phoneNumber, String emergencyContact1, String emergencyContact2, String emergencyContact3, String refreshToken, String deviceToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
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
    public void modify(PatchReq patchReq) {
        this.email = patchReq.getEmail();
        this.name = patchReq.getName();
        this.age = patchReq.getAge();
        this.phoneNumber = patchReq.getPhoneNumber();
    }
    public void saveDeviceToken(String str){
        this.deviceToken = str;
    }
    public void saveRefreshToken(String str){
        this.refreshToken = str;
    }

}
