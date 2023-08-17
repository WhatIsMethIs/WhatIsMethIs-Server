package com.WhatIsMethIs.src.controller;

import com.WhatIsMethIs.src.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/medicines")
@Tag(name = "MEDICINE", description = "약물 정보 API")
public class MedicineController {
    private final MedicineService medicineService;

    private final String openApiEndPoint = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
    private final String openApiserviceKey = "jn2o5j7ULjTWI9MuM0HbTPpUUOaIATwvwylC36Oed1AHs5mqh0rSDynNyuDROqOsn4ZcyZ9LUUMgixImyDNkhQ%3D%3D";
    private final int numOfRows = 10;
    private final String openApiResponseType = "json";

    /***
     * 2.1.1 약물 정보 전체 조회
     * [GET] /medicines?pageNo={pageNo}
     */
    @Operation(method = "GET", description = "약물 정보 전체를 조회하는 api로, 10개를 한 페이지로 제공", tags = "MEDICINE", summary = "2.1.1 약물 정보 전체 조회")
    @ResponseBody
    @GetMapping("")
    public String getMedicines(@RequestParam("pageNo") int pageNo) throws IOException {
        String apiUrl = openApiEndPoint + "?" +
                "serviceKey=" + openApiserviceKey +
                "&pageNo=" + pageNo +
                "&numOfRows=" + numOfRows +
                "&type=" + openApiResponseType;
        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader bufferdReader = new BufferedReader((new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8)));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLIne;

        while((inputLIne = bufferdReader.readLine()) != null){
            stringBuilder.append(inputLIne);
        }
        bufferdReader.close();

        return stringBuilder.toString();
    }
}
