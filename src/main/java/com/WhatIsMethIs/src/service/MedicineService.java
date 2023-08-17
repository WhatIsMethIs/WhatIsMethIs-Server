package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.src.dto.medicine.MedicineDto;
import com.WhatIsMethIs.src.dto.medicine.MedicineResponseDto;
import com.WhatIsMethIs.src.entity.Medicine;
import com.WhatIsMethIs.src.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.WhatIsMethIs.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    private final String openApiEndPoint = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
    private final String openApiserviceKey = "jn2o5j7ULjTWI9MuM0HbTPpUUOaIATwvwylC36Oed1AHs5mqh0rSDynNyuDROqOsn4ZcyZ9LUUMgixImyDNkhQ%3D%3D";
    private final int numOfRows = 10;
    private final String openApiResponseType = "json";

    public MedicineResponseDto getMedicinesFromOpenApi(int pageNo) throws IOException {
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

        String response = stringBuilder.toString();

        JSONObject jsonObjectBody = new JSONObject(response).getJSONObject("body");

        MedicineResponseDto medicineResponseDto = new MedicineResponseDto();

        medicineResponseDto.setPageNo(jsonObjectBody.getInt("pageNo"));
        medicineResponseDto.setTotalCount(jsonObjectBody.getInt("totalCount"));
        medicineResponseDto.setNumOfRows(jsonObjectBody.getInt("numOfRows"));

        JSONArray jsonItems = jsonObjectBody.getJSONArray("items");
        for(Object item : jsonItems){
            JSONObject jsonItem = (JSONObject) item;

            MedicineDto medicineDto = new MedicineDto();

            if(!jsonItem.get("entpName").equals(null)){
                medicineDto.setEntpName(jsonItem.getString("entpName"));
            }
            if(!jsonItem.get("itemName").equals(null)){
                medicineDto.setItemName(jsonItem.getString("itemName"));
            }
            if(!jsonItem.get("itemSeq").equals(null)){
                medicineDto.setItemSeq(jsonItem.getString("itemSeq"));
            }
            if(!jsonItem.get("efcyQesitm").equals(null)){
                medicineDto.setEfcyQesitm(jsonItem.getString("efcyQesitm"));
            }
            if(!jsonItem.get("useMethodQesitm").equals(null)){
                medicineDto.setUseMethodQesitm(jsonItem.getString("useMethodQesitm"));
            }
            if(!jsonItem.get("atpnWarnQesitm").equals(null)){
                medicineDto.setAtpnWarnQesitm(jsonItem.getString("atpnWarnQesitm"));
            }
            if(!jsonItem.get("atpnQesitm").equals(null)){
                medicineDto.setAtpnQesitm(jsonItem.getString("atpnQesitm"));
            }
            if(!jsonItem.get("intrcQesitm").equals(null)){
                medicineDto.setIntrcQesitm(jsonItem.getString("intrcQesitm"));
            }
            if(!jsonItem.get("seQesitm").equals(null)){
                medicineDto.setSeQesitm(jsonItem.getString("seQesitm"));
            }
            if(!jsonItem.get("depositMethodQesitm").equals(null)){
                medicineDto.setDepositMethodQesitm(jsonItem.getString("depositMethodQesitm"));
            }
            if(!jsonItem.get("openDe").equals(null)){
                medicineDto.setOpenDe(jsonItem.getString("openDe"));
            }
            if(!jsonItem.get("updateDe").equals(null)){
                medicineDto.setUpdateDe(jsonItem.getString("updateDe"));
            }
            if(!jsonItem.get("itemImage").equals(null)){
                medicineDto.setItemImage(jsonItem.getString("itemImage"));
            }
            if(!jsonItem.get("bizrno").equals(null)){
                medicineDto.setBizrno(jsonItem.getString("bizrno"));
            }

            medicineResponseDto.getMedicines().add(medicineDto);
        }

        return medicineResponseDto;
    }

    public MedicineResponseDto getMedicinesFromOpenApiByItemName(String itemName, int pageNo) throws IOException {
        String apiUrl = openApiEndPoint + "?" +
                "serviceKey=" + openApiserviceKey +
                "&pageNo=" + pageNo +
                "&numOfRows=" + numOfRows +
                "&type=" + openApiResponseType +
                "&itemName=" + URLEncoder.encode(itemName, "utf-8");
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

        String response = stringBuilder.toString();

        MedicineResponseDto medicineResponseDto = null;
        if(new JSONObject(response).keySet().contains("body")){
            JSONObject jsonObjectBody = new JSONObject(response).getJSONObject("body");

            medicineResponseDto = new MedicineResponseDto();

            medicineResponseDto.setPageNo(jsonObjectBody.getInt("pageNo"));
            medicineResponseDto.setTotalCount(jsonObjectBody.getInt("totalCount"));
            medicineResponseDto.setNumOfRows(jsonObjectBody.getInt("numOfRows"));

            JSONArray jsonItems = jsonObjectBody.getJSONArray("items");
            for(Object item : jsonItems){
                JSONObject jsonItem = (JSONObject) item;

                MedicineDto medicineDto = new MedicineDto();

                if(!jsonItem.get("entpName").equals(null)){
                    medicineDto.setEntpName(jsonItem.getString("entpName"));
                }
                if(!jsonItem.get("itemName").equals(null)){
                    medicineDto.setItemName(jsonItem.getString("itemName"));
                }
                if(!jsonItem.get("itemSeq").equals(null)){
                    medicineDto.setItemSeq(jsonItem.getString("itemSeq"));
                }
                if(!jsonItem.get("efcyQesitm").equals(null)){
                    medicineDto.setEfcyQesitm(jsonItem.getString("efcyQesitm"));
                }
                if(!jsonItem.get("useMethodQesitm").equals(null)){
                    medicineDto.setUseMethodQesitm(jsonItem.getString("useMethodQesitm"));
                }
                if(!jsonItem.get("atpnWarnQesitm").equals(null)){
                    medicineDto.setAtpnWarnQesitm(jsonItem.getString("atpnWarnQesitm"));
                }
                if(!jsonItem.get("atpnQesitm").equals(null)){
                    medicineDto.setAtpnQesitm(jsonItem.getString("atpnQesitm"));
                }
                if(!jsonItem.get("intrcQesitm").equals(null)){
                    medicineDto.setIntrcQesitm(jsonItem.getString("intrcQesitm"));
                }
                if(!jsonItem.get("seQesitm").equals(null)){
                    medicineDto.setSeQesitm(jsonItem.getString("seQesitm"));
                }
                if(!jsonItem.get("depositMethodQesitm").equals(null)){
                    medicineDto.setDepositMethodQesitm(jsonItem.getString("depositMethodQesitm"));
                }
                if(!jsonItem.get("openDe").equals(null)){
                    medicineDto.setOpenDe(jsonItem.getString("openDe"));
                }
                if(!jsonItem.get("updateDe").equals(null)){
                    medicineDto.setUpdateDe(jsonItem.getString("updateDe"));
                }
                if(!jsonItem.get("itemImage").equals(null)){
                    medicineDto.setItemImage(jsonItem.getString("itemImage"));
                }
                if(!jsonItem.get("bizrno").equals(null)){
                    medicineDto.setBizrno(jsonItem.getString("bizrno"));
                }

                medicineResponseDto.getMedicines().add(medicineDto);
            }

        }

        return medicineResponseDto;
    }

    public List<Medicine> getMedicines() throws BaseException{
        try{
            return medicineRepository.findAll();
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
