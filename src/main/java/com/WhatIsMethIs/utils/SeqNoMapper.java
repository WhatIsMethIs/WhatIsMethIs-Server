package com.WhatIsMethIs.utils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class SeqNoMapper {
    private final ClassPathResource resource = new ClassPathResource("json/regNo_seqNo.json");
    private JSONObject json;

    public SeqNoMapper() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String s = "";
        String stringForJson = "";
        while((s = br.readLine()) != null){
            stringForJson += s;
        }
        this.json = new JSONObject(stringForJson);
    }

    public String getSeqNoByRegNo(String regNo) throws IOException {
        return this.json.getString(regNo);
    }
}
