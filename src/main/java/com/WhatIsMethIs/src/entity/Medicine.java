package com.WhatIsMethIs.src.entity;

import com.WhatIsMethIs.src.dto.medicine.MedicineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "medicine")
@NoArgsConstructor
@Schema(description = "약물 정보 객체")
public class Medicine {
    @Column(length = 4000, name = "entp_name")
    public String entpName;
    @Column(length = 4000, name = "item_name")
    public String itemName;
    @Id
    @Column(length = 10, name = "item_seq")
    public String itemSeq;
    @Lob
    @Column(name = "efcy_qesitm")
    public String efcyQesitm;
    @Lob
    @Column(name = "use_method_qesitm")
    public String useMethodQesitm;
    @Lob
    @Column(name = "atpn_warn_qesitm")
    public String atpnWarnQesitm;
    @Lob
    @Column(name = "atpn_qesitm")
    public String atpnQesitm;
    @Lob
    @Column(name = "intrc_qesitm")
    public String intrcQesitm;
    @Lob
    @Column(name = "se_qesitm")
    public String seQesitm;
    @Lob
    @Column(name = "deposit_method_qesitm")
    public String depositMethodQesitm;
    @Column(name = "open_de")
    public String openDe;
    @Column(name = "update_de")
    public String updateDe;
    @Column(name = "item_image")
    public String itemImage;
    @Column(name = "bizrno")
    public String bizrno;

    public static Medicine toEntity(MedicineDto medicineDto){
        Medicine medicine = new Medicine();

        if(medicineDto.getEntpName() != null){
            medicine.entpName = medicineDto.getEntpName();
        }
        if(medicineDto.getItemName() != null){
            medicine.itemName = medicineDto.getItemName();
        }
        if(medicineDto.getItemSeq() != null){
            medicine.itemSeq = medicineDto.getItemSeq();
        }
        if(medicineDto.getEfcyQesitm() != null){
            medicine.efcyQesitm = medicineDto.getEfcyQesitm();
        }
        if(medicineDto.getUseMethodQesitm() != null){
            medicine.useMethodQesitm = medicineDto.getUseMethodQesitm();
        }
        if(medicineDto.getAtpnWarnQesitm() != null){
            medicine.atpnWarnQesitm = medicineDto.getAtpnWarnQesitm();
        }
        if(medicineDto.getAtpnQesitm() != null){
            medicine.atpnQesitm = medicine.getAtpnQesitm();
        }
        if(medicineDto.getIntrcQesitm() != null){
            medicine.intrcQesitm = medicineDto.getIntrcQesitm();
        }
        if(medicineDto.getSeQesitm() != null){
            medicine.seQesitm = medicineDto.getSeQesitm();
        }
        if(medicineDto.getDepositMethodQesitm() != null){
            medicine.depositMethodQesitm = medicineDto.getDepositMethodQesitm();
        }
        if(medicineDto.getOpenDe() != null){
            medicine.openDe = medicineDto.getOpenDe();
        }
        if(medicineDto.getUpdateDe() != null){
            medicine.updateDe = medicineDto.getUpdateDe();
        }
        if(medicineDto.getItemImage() != null){
            medicine.itemImage = medicineDto.getItemImage();
        }
        if(medicineDto.getBizrno() != null){
            medicine.bizrno = medicineDto.getBizrno();
        }

        return medicine;
    }
}
