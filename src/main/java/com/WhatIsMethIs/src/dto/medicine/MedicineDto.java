package com.WhatIsMethIs.src.dto.medicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineDto {
    private int id;
    private String resultCode;
    private String resultMsg;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private String entpName;
    private String itemName;
    private String itemSeq;
    private String efcyQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String useMethodQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String atpnWarnQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String atpnQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String intrcQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String seQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String depositMethodQesitm;   // 2GB 넘어 가면 별도의 처리 필요
    private String openDe;
    private String updateDe;
    private String itemImage;
}
