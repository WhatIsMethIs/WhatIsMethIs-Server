package com.WhatIsMethIs.src.dto.medicine;

import com.WhatIsMethIs.src.entity.Medicine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineDto {
    private String entpName;
    private String itemName;
    private String itemSeq;     // id: 품목기준코드
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
    private String bizrno;

    public static MedicineDto toDto(Medicine medicine){
        MedicineDto medicineDto = new MedicineDto();

        if(medicine.getEntpName() != null){
            medicineDto.setEntpName(medicine.getEntpName());
        }
        if(medicine.getItemName() != null){
            medicineDto.setItemName(medicine.getItemName());
        }
        if(medicine.getItemSeq() != null){
            medicineDto.setItemSeq(medicine.getItemSeq());
        }
        if(medicine.getEfcyQesitm() != null){
            medicineDto.setEfcyQesitm(medicine.getEfcyQesitm());
        }
        if(medicine.getUseMethodQesitm() != null){
            medicineDto.setUseMethodQesitm(medicine.getUseMethodQesitm());
        }
        if(medicine.getAtpnWarnQesitm() != null){
            medicineDto.setAtpnWarnQesitm(medicine.getAtpnWarnQesitm());
        }
        if(medicine.getAtpnQesitm() != null){
            medicineDto.setAtpnQesitm(medicine.getAtpnQesitm());
        }
        if(medicine.getIntrcQesitm() != null){
            medicineDto.setIntrcQesitm(medicine.getIntrcQesitm());
        }
        if(medicine.getSeQesitm() != null){
            medicineDto.setSeQesitm(medicineDto.getSeQesitm());
        }
        if(medicine.getDepositMethodQesitm() != null){
            medicineDto.setDepositMethodQesitm(medicine.getDepositMethodQesitm());
        }
        if(medicine.getOpenDe() != null){
            medicineDto.setOpenDe(medicineDto.getOpenDe());
        }
        if(medicine.getUpdateDe() != null){
            medicineDto.setUpdateDe(medicineDto.getUpdateDe());
        }
        if(medicine.getItemImage() != null){
            medicineDto.setItemImage(medicine.getItemImage());
        }
        if(medicine.getBizrno() != null){
            medicineDto.setBizrno(medicineDto.getBizrno());
        }
        return medicineDto;
    }
}
