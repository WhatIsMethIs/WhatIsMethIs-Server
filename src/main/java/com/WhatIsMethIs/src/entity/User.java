package com.WhatIsMethIs.src.entity;

import com.WhatIsMethIs.src.dto.user.ModifyEmergencyReq;
import com.WhatIsMethIs.src.dto.user.ModifyReq;
import jakarta.persistence.*;
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
    @Column(length = 20, nullable = false, name="phone_number")
    public String phoneNumber;
    @Column(length = 20, name = "emergency_contact1")
    public String emergencyContact1;
    @Column(length = 20, name = "emergency_contact2")
    public String emergencyContact2;
    @Column(length = 20, name = "emergency_contact3")
    public String emergencyContact3;

    @Builder
    public User(int id, String email, String password, String name, String age, String phoneNumber, String emergencyContact1, String emergencyContact2, String emergencyContact3) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.emergencyContact1 = emergencyContact1;
        this.emergencyContact2 = emergencyContact2;
        this.emergencyContact3 = emergencyContact3;
    }

    public void modifyEmergencyContact(ModifyEmergencyReq modifyEmergencyReq) {
        this.emergencyContact1 = modifyEmergencyReq.getContact1();
        this.emergencyContact2 = modifyEmergencyReq.getContact2();
        this.emergencyContact3 = modifyEmergencyReq.getContact3();
    }
    public void modify(ModifyReq modifyReq) {
        this.email = modifyReq.getEmail();
        this.name = modifyReq.getName();
        this.age = modifyReq.getAge();
        this.phoneNumber = modifyReq.getPhoneNumber();
    }

}
