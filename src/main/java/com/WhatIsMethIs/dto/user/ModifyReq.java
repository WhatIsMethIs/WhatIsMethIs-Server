package com.WhatIsMethIs.dto.user;

import com.WhatIsMethIs.entity.User;
import lombok.*;

@Getter
@NoArgsConstructor
public class ModifyReq {
    private String email;
    private String name;
    private String age;
    private String phoneNumber;
}
