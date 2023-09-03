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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    // api 공통
    private final int numOfRows = 10;
    private final String openApiResponseType = "json";
    private final String openApiserviceKey = "jn2o5j7ULjTWI9MuM0HbTPpUUOaIATwvwylC36Oed1AHs5mqh0rSDynNyuDROqOsn4ZcyZ9LUUMgixImyDNkhQ%3D%3D";

    // 식품의약품안전처_의약품개요정보(e약은요)
    private final String openApiEndPoint_easyDrugInfo = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
    
    // 식품의약품안전처_의약품 낱알식별 정보
    private final String openApiEndPoint_minimumInfo = "https://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01";
    
    // 모델 서버
    private final String modelServerUrl = "http://ec2-3-37-100-106.ap-northeast-2.compute.amazonaws.com:5000/medicines/identify";
    private final String boundary="--------------------------";
    private final String crlf = "\r\n";

    public MedicineResponseDto getMedicinesFromOpenApi(int pageNo) throws IOException, BaseException {
        String apiUrl = openApiEndPoint_easyDrugInfo + "?" +
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

        if(response.startsWith("<")){
            throw new BaseException(RESPONSE_IS_XML);
        }

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

            //medicineRepository.save(Medicine.toEntity(medicineDto));
        }

        return medicineResponseDto;
    }

    public MedicineResponseDto getMedicinesFromOpenApiByItemName(String itemName, int pageNo) throws IOException, BaseException {
        String apiUrl = openApiEndPoint_easyDrugInfo + "?" +
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

        if(response.startsWith("<")){
            throw new BaseException(RESPONSE_IS_XML);
        }

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

                //medicineRepository.save(Medicine.toEntity(medicineDto));
            }

        }

        return medicineResponseDto;
    }

    public MedicineResponseDto getMedicinesFromOpenApiByItemSeq(String itemSeq) throws IOException, BaseException {
        String apiUrl = openApiEndPoint_easyDrugInfo + "?" +
                "serviceKey=" + openApiserviceKey +
                "&itemSeq=" + itemSeq +
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

        if(response.startsWith("<")){
            throw new BaseException(RESPONSE_IS_XML);
        }

        JSONObject jsonObjectBody = new JSONObject(response).getJSONObject("body");

        MedicineResponseDto medicineResponseDto = new MedicineResponseDto();

        int totalCountOfResponse = jsonObjectBody.getInt("totalCount");

        if(totalCountOfResponse > 0) {
            medicineResponseDto.setPageNo(jsonObjectBody.getInt("pageNo"));
            medicineResponseDto.setTotalCount(totalCountOfResponse);
            medicineResponseDto.setNumOfRows(jsonObjectBody.getInt("numOfRows"));

            JSONArray jsonItems = jsonObjectBody.getJSONArray("items");
            for (Object item : jsonItems) {
                JSONObject jsonItem = (JSONObject) item;

                MedicineDto medicineDto = new MedicineDto();

                if (!jsonItem.get("entpName").equals(null)) {
                    medicineDto.setEntpName(jsonItem.getString("entpName"));
                }
                if (!jsonItem.get("itemName").equals(null)) {
                    medicineDto.setItemName(jsonItem.getString("itemName"));
                }
                if (!jsonItem.get("itemSeq").equals(null)) {
                    medicineDto.setItemSeq(jsonItem.getString("itemSeq"));
                }
                if (!jsonItem.get("efcyQesitm").equals(null)) {
                    medicineDto.setEfcyQesitm(jsonItem.getString("efcyQesitm"));
                }
                if (!jsonItem.get("useMethodQesitm").equals(null)) {
                    medicineDto.setUseMethodQesitm(jsonItem.getString("useMethodQesitm"));
                }
                if (!jsonItem.get("atpnWarnQesitm").equals(null)) {
                    medicineDto.setAtpnWarnQesitm(jsonItem.getString("atpnWarnQesitm"));
                }
                if (!jsonItem.get("atpnQesitm").equals(null)) {
                    medicineDto.setAtpnQesitm(jsonItem.getString("atpnQesitm"));
                }
                if (!jsonItem.get("intrcQesitm").equals(null)) {
                    medicineDto.setIntrcQesitm(jsonItem.getString("intrcQesitm"));
                }
                if (!jsonItem.get("seQesitm").equals(null)) {
                    medicineDto.setSeQesitm(jsonItem.getString("seQesitm"));
                }
                if (!jsonItem.get("depositMethodQesitm").equals(null)) {
                    medicineDto.setDepositMethodQesitm(jsonItem.getString("depositMethodQesitm"));
                }
                if (!jsonItem.get("openDe").equals(null)) {
                    medicineDto.setOpenDe(jsonItem.getString("openDe"));
                }
                if (!jsonItem.get("updateDe").equals(null)) {
                    medicineDto.setUpdateDe(jsonItem.getString("updateDe"));
                }
                if (!jsonItem.get("itemImage").equals(null)) {
                    medicineDto.setItemImage(jsonItem.getString("itemImage"));
                }
                if (!jsonItem.get("bizrno").equals(null)) {
                    medicineDto.setBizrno(jsonItem.getString("bizrno"));
                }

                medicineResponseDto.getMedicines().add(medicineDto);

                medicineRepository.save(Medicine.toEntity(medicineDto));
            }
        }
        else{
            apiUrl = openApiEndPoint_minimumInfo + "?" +
                    "serviceKey=" + openApiserviceKey +
                    "&item_seq=" + itemSeq +
                    "&numOfRows=" + numOfRows +
                    "&type=" + openApiResponseType +
                    "&pageNo=1";

            url = new URL(apiUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            bufferdReader = new BufferedReader((new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8)));
            stringBuilder = new StringBuilder();
            inputLIne = "";

            while((inputLIne = bufferdReader.readLine()) != null){
                stringBuilder.append(inputLIne);
            }
            bufferdReader.close();

            response = stringBuilder.toString();
            System.out.println(response);

            if(response.startsWith("<")){
                throw new BaseException(RESPONSE_IS_XML);
            }
            jsonObjectBody = new JSONObject(response).getJSONObject("body");
            medicineResponseDto.setPageNo(jsonObjectBody.getInt("pageNo"));
            medicineResponseDto.setTotalCount(jsonObjectBody.getInt("totalCount"));
            medicineResponseDto.setNumOfRows(jsonObjectBody.getInt("numOfRows"));

            JSONArray jsonItems = jsonObjectBody.getJSONArray("items");
            for (Object item : jsonItems) {
                JSONObject jsonItem = (JSONObject) item;

                MedicineDto medicineDto = new MedicineDto();

                if (!jsonItem.get("ENTP_NAME").equals(null)) {
                    medicineDto.setEntpName(jsonItem.getString("ENTP_NAME"));
                }
                if (!jsonItem.get("ITEM_NAME").equals(null)) {
                    medicineDto.setItemName(jsonItem.getString("ITEM_NAME"));
                }
                if (!jsonItem.get("IMG_REGIST_TS").equals(null)) {
                    medicineDto.setOpenDe(jsonItem.getString("IMG_REGIST_TS"));
                }
                if (!jsonItem.get("CHANGE_DATE").equals(null)) {
                    medicineDto.setUpdateDe(jsonItem.getString("CHANGE_DATE"));
                }
                if (!jsonItem.get("ITEM_IMAGE").equals(null)) {
                    medicineDto.setItemImage(jsonItem.getString("ITEM_IMAGE"));
                }
                if (!jsonItem.get("EDI_CODE").equals(null)) {
                    medicineDto.setBizrno(jsonItem.getString("EDI_CODE"));
                }
                medicineDto.setItemSeq("정보 없음.");
                medicineDto.setEfcyQesitm("정보 없음.");
                medicineDto.setUseMethodQesitm("정보 없음.");
                medicineDto.setAtpnWarnQesitm("정보 없음.");
                medicineDto.setAtpnQesitm("정보 없음.");
                medicineDto.setIntrcQesitm("정보 없음.");
                medicineDto.setSeQesitm("정보 없음.");
                medicineDto.setDepositMethodQesitm("정보 없음.");

                medicineResponseDto.getMedicines().add(medicineDto);

                medicineRepository.save(Medicine.toEntity(medicineDto));
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

    public MedicineResponseDto getMedicinesByItemSeq(String itemSeq) throws BaseException {
        MedicineResponseDto medicineResponseDto = null;

        Medicine medicine = medicineRepository.findById(itemSeq).orElse(null);
        try{
            if(medicine == null){
                medicineResponseDto = getMedicinesFromOpenApiByItemSeq(itemSeq);
            }
            else{
                medicineResponseDto = new MedicineResponseDto();
                medicineResponseDto.setPageNo(1);
                medicineResponseDto.setTotalCount(1);
                medicineResponseDto.setNumOfRows(1);
                medicineResponseDto.getMedicines().add(MedicineDto.toDto(medicine));
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

        return medicineResponseDto;
    }

    public String getMedicineByImage(List<MultipartFile> multipartFiles)
            throws BaseException{

        //MedicineResponseDto medicineResponseDto = new MedicineResponseDto();
        String rt = "";

        try{
            URL url = new URL(modelServerUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);

            urlConnection.setRequestProperty("Connection", "keep-alive");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + this.boundary);

            OutputStream outputStream = urlConnection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

            if (multipartFiles.size() > 1){
                throw new BaseException(TOO_MANY_FILES);
            }
            else{
                MultipartFile multipartFile = multipartFiles.get(0);
                writer.append("--" + boundary).append(this.crlf);
                //System.out.println("--" + boundary + this.crlf);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + multipartFile.getOriginalFilename() + "\"").append(this.crlf);
                //System.out.println("Content-Disposition: form-data; name=\"file\"; filename=\"" + multipartFile.getOriginalFilename() + "\"" + this.crlf);
                writer.append("Content-Type: " + multipartFile.getContentType()).append(this.crlf);
                //System.out.println("Content-Type: " + multipartFile.getContentType() + this.crlf);
                writer.append("Content-Transfer-Encoding: binary").append(this.crlf);
                //System.out.println("Content-Transfer-Encoding: binary" + this.crlf);
                writer.append(this.crlf);
                //System.out.println(this.crlf);
                writer.flush();

                InputStream inputStream = multipartFile.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while((bytesRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();

                writer.append(this.crlf).flush();
            }

            writer.append("--").append(boundary).append("--").append(this.crlf);
            writer.flush();

            InputStreamReader inputStreamReader = (new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader bufferdReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String inputLIne;

            while((inputLIne = bufferdReader.readLine()) != null){
                stringBuilder.append(inputLIne);
            }
            bufferdReader.close();

            String response = stringBuilder.toString();
            if(response.startsWith("<")){
                throw new BaseException(RESPONSE_IS_XML);
            }

            rt = new JSONObject(response).getString("class_id");
            System.out.println(rt);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new BaseException(INVALID_URL);
        }

        //return medicineResponseDto;
        return rt;
    }


}
