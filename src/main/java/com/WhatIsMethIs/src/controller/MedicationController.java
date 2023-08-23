package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.medication.MedicationReqDto.*;
import com.WhatIsMethIs.src.dto.medication.MedicationResDto.*;
import com.WhatIsMethIs.src.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/app/medications")
@RestController
@Tag(name = "Medication", description = "복약정보 API")
public class MedicationController {
    private final MedicationService medicationService;

    /**
     * 3.1.1 index로 복약정보 조회
     * [GET] /medications/:index
     */
    @Operation(description = "복약정보 1개를 조회하는 api로, (약물명, 약물이미지, 1회 복용량, 복용시작/종료일, 복용시간, 복용주기, 알림시간, 설명)을 반환합니다. (단, 본인의 복약정보만 조회 가능) " +
            "* nullable : notificationTime,", summary = "3.1.1 복약정보 index로 복약정보 1개 조회")
    @GetMapping("/{index}")
    public BaseResponse<MedicationInfoRes> getMedicationWithIndex(@PathVariable("index") Long index) throws BaseException {
        return new BaseResponse<>(medicationService.getMedicationWithIndex(index));
    }

    /**
     * 3.1.3 복약정보 전체 조회
     * [GET] /medications/all/:page
     */
    @Operation(description = "복약정보 전체를 조회하는 api로, MedicationShortInfoRes DTO(약물명, 약물이미지, 복용시간, 1회 복용량)와 전체 페이지 수를 반환합니다. " +
            "(단, page 변수는 0부터 시작)",
            summary = "3.1.3 복약정보 전체 조회")
    @GetMapping("/all/{page}")
    public BaseResponse<MedicationShortInfoListRes> getAllMedication(@PathVariable("page") int page) throws BaseException {
        return new BaseResponse<>(medicationService.getMedicationShortInfoList(page));
    }

    /**
     * 3.2.1 복약정보 등록
     * [POST] /medications
     */
    @Operation(description = "복약정보를 등록하는 api로, 등록된 복약정보 index를 반환합니다. ",
            summary = "3.2.1 복약정보 등록")
    @PostMapping("")
    public BaseResponse<MedicationIdRes> createMedication(@RequestBody MedicationInfoReq medicationInfo) throws BaseException {
        return new BaseResponse<>(medicationService.createMedication(medicationInfo));
    }

    /**
     * 3.3.1 index로 복약정보 수정
     * [PATCH] /medications/:index
     */
    @Operation(description = "index로 복약정보를 수정하는 api로, 수정된 복약정보 index를 반환합니다. ",
            summary = "3.3.1 복약정보 수정")
    @PatchMapping("/{index}")
    public BaseResponse<MedicationIdRes> updateMedication(@PathVariable("index") Long index, @RequestBody MedicationInfoReq medicationInfo) throws BaseException {
        return new BaseResponse<>(medicationService.updateMedication(index, medicationInfo));
    }

    /**
     * 3.4.1 index 리스트로 복약정보 삭제
     * [DELETE] /medications
     */
    @Operation(description = "삭제할 복약정보 index들이 담긴 List로 복약정보를 삭제합니다. ",
            summary = "3.4.1 복약정보 삭제")
    @DeleteMapping("")
    public BaseResponse<String> deleteMedication(@RequestParam List<Long> medicationIdList) throws BaseException {
        medicationService.deleteMedication(medicationIdList);
        return new BaseResponse<>("복약정보들이 성공적으로 삭제되었습니다.");
    }
}
