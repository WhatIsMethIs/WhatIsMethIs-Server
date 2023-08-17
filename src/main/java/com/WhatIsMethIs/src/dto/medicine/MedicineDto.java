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

        if(!medicine.getEntpName().equals(null)){
            medicineDto.setEntpName(medicine.getEntpName());
        }
        if(!medicine.getItemName().equals(null)){
            medicineDto.setItemName(medicine.getItemName());
        }
        if(!medicine.getItemSeq().equals(null)){
            medicineDto.setItemSeq(medicine.getItemSeq());
        }
        if(!medicine.getEfcyQesitm().equals(null)){
            medicineDto.setEfcyQesitm(medicine.getEfcyQesitm());
        }
        if(!medicine.getUseMethodQesitm().equals(null)){
            medicineDto.setUseMethodQesitm(medicine.getUseMethodQesitm());
        }
        if(!medicine.getAtpnWarnQesitm().equals(null)){
            medicineDto.setAtpnWarnQesitm(medicine.getAtpnWarnQesitm());
        }
        if(!medicine.getAtpnQesitm().equals(null)){
            medicineDto.setAtpnQesitm(medicine.getAtpnQesitm());
        }
        if(!medicine.getIntrcQesitm().equals(null)){
            medicineDto.setIntrcQesitm(medicine.getIntrcQesitm());
        }
        if(!medicine.getSeQesitm().equals(null)){
            medicineDto.setSeQesitm(medicineDto.getSeQesitm());
        }
        if(!medicine.getDepositMethodQesitm().equals(null)){
            medicineDto.setDepositMethodQesitm(medicine.getDepositMethodQesitm());
        }
        if(!medicine.getOpenDe().equals(null)){
            medicineDto.setOpenDe(medicineDto.getOpenDe());
        }
        if(!medicine.getUpdateDe().equals(null)){
            medicineDto.setUpdateDe(medicineDto.getUpdateDe());
        }
        if(!medicine.getItemImage().equals(null)){
            medicineDto.setItemImage(medicine.getItemImage());
        }
        if(!medicine.getBizrno().equals(null)){
            medicineDto.setBizrno(medicineDto.getBizrno());
        }
        return medicineDto;
    }
}
