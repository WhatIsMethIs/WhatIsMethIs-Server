package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.medication.MedicationResDto.*;
import com.WhatIsMethIs.src.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/app/medications")
@RestController
@Tag(name = "Medication API", description = "복약정보 API")
public class MedicationController {
    private final MedicationService medicationService;

    /**
     * 3.1.1 index로 복약정보 조회
     * [GET] /medications/:index
     */
    @Operation(method = "GET",
            description = "복약정보 1개을 조회하는 api로, MedicationInfoRes DTO를 반환합니다. (단, 본인의 복약정보만 조회 가능) ",
                    summary = "3.1.1 복약정보 index로 복약정보 1개 조회")
    @GetMapping("/{index}")
    public BaseResponse<MedicationInfoRes> getMedicationWithIndex(@PathVariable("index") Long index) throws BaseException {
        return new BaseResponse<>(medicationService.getMedicationWithIndex(index));
    }
}
