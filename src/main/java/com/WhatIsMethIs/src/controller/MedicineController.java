package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.medicine.MedicineResponseDto;
import com.WhatIsMethIs.src.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/medicines")
@Tag(name = "MEDICINE", description = "약물 정보 API")
public class MedicineController {
    private final MedicineService medicineService;

    /**
     * 2.1.1 약물 정보 전체 조회
     * 2.1.2 약물명으로 검색
     * [GET] /medicines?pageNo={pageNo}&itemName={itemName}
     */
    @Operation(method = "GET", description = "약물 정보 전체를 조회하는 api로, 10개를 한 페이지로 제공", tags = "MEDICINE", summary = "2.1.1 약물 정보 전체 조회")
    @ResponseBody
    @GetMapping("")
    public BaseResponse<MedicineResponseDto> getMedicines(@RequestParam HashMap<String, String> paramMap) throws IOException {
        MedicineResponseDto medicineResponseDto = null;
        
        int pageNo = Integer.parseInt(paramMap.get("pageNo"));
        if(paramMap.containsKey("itemName") && !paramMap.containsKey("itemSeq")){
            medicineResponseDto = medicineService.getMedicinesFromOpenApiByItemName(paramMap.get("itemName"), pageNo);
        }
        /*else if(!paramMap.containsKey("itemName") && paramMap.containsKey("itemSeq")){
            medicineResponseDto = medicineService.
        }*/
        else{
            medicineResponseDto = medicineService.getMedicinesFromOpenApi(pageNo);
        }

        return new BaseResponse<>(medicineResponseDto);
    }
}
