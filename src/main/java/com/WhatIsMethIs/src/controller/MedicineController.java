package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponse;
import com.WhatIsMethIs.src.dto.medicine.MedicineDto;
import com.WhatIsMethIs.src.dto.medicine.MedicineResponseDto;
import com.WhatIsMethIs.src.dto.medicine.PillImageDto;
import com.WhatIsMethIs.src.service.MedicineService;
import com.WhatIsMethIs.src.service.PillImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.WhatIsMethIs.config.BaseResponseStatus.OPEN_API_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/medicines")
@Tag(name = "MEDICINE", description = "약물 정보 API")
public class MedicineController {
    private final MedicineService medicineService;
    private final PillImageService pillImageService;

    /**
     * 2.1.1 약물 정보 전체 조회
     * 2.1.2 약물명으로 검색
     * 2.1.3 약물 물품 번호로 검색
     * [GET] /medicines?pageNo={pageNo}&itemName={itemName}&itemSeq={itemSeq}
     */
    @Operation(method = "GET", description = "약물 정보 전체를 조회하는 api로, 10개를 한 페이지로 제공", tags = "MEDICINE", summary = "2.1.1 약물 정보 전체 조회")
    @ResponseBody
    @GetMapping("")
    public BaseResponse<MedicineResponseDto> getMedicines(@RequestParam HashMap<String, String> paramMap) throws IOException, BaseException {
        MedicineResponseDto medicineResponseDto = null;
        
        int pageNo = 0;
        try{
            if(paramMap.containsKey("pageNo")){
                pageNo = Integer.parseInt(paramMap.get("pageNo"));
            }

            if(paramMap.containsKey("itemName") && !paramMap.containsKey("itemSeq")){
                medicineResponseDto = medicineService.getMedicinesFromOpenApiByItemName(paramMap.get("itemName"), pageNo);
            }
            else if(!paramMap.containsKey("itemName") && paramMap.containsKey("itemSeq")){
                medicineResponseDto = medicineService.getMedicinesByItemSeq(paramMap.get("itemSeq"));
            }
            else{
                medicineResponseDto = medicineService.getMedicinesFromOpenApi(pageNo);
            }

            return new BaseResponse<>(medicineResponseDto);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * TODO 2.1.4 약물 이미지로 식별
     * [POST] /medicines/identify
     */
    @PostMapping("/identify")
    public BaseResponse<MedicineResponseDto> postPillImageAndGetInfo(
            @Valid @RequestParam("files") List<MultipartFile> images
    )
    {
        MedicineResponseDto medicineResponseDto = null;

        try{
            // TODO 모델 서버에 식별 요청
            // 현재는 이미지와 관계 없이 임의의 약물 정보 송신
            medicineResponseDto = medicineService.getMedicinesByItemSeq("202001927");

            // 이미지 로컬 디렉토리에 저장
            //pillImageService.addPillImage(images);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

        return new BaseResponse<>(medicineResponseDto);
    }
}
