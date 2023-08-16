package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.medication.MedicationResDto.*;
import com.WhatIsMethIs.src.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/medications")
@RestController
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping("/{index}")
    //Todo: token 추가, swagger 추가
    public BaseResponse<MedicationInfoRes> getMedicationWithIndex(@PathVariable("index") Long index){
        return new BaseResponse<>(medicationService.getMedicationWithIndex(index));
    }
}
