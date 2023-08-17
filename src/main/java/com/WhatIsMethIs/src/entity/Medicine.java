package com.WhatIsMethIs.src.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "medicine")
@NoArgsConstructor
@Schema(description = "약물 정보 객체")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(length = 4, nullable = false)
    public String resultCode;
    @Column(length = 50, nullable = false)
    public String resultMsg;
    @Column
    public int numOfRows;
    @Column
    public int pageNo;
    @Column
    public int totalCount;
    @Column(length = 4000)
    public String entpName;
    @Column(length = 4000)
    public String itemName;
    @Column(length = 4000)
    public String itemSeq;
    @Lob
    public String efcyQesitm;
    @Lob
    public String useMethodQesitm;
    @Lob
    public String atpnWarnQesitm;
    @Lob
    public String atpnQesitm;
    @Lob
    public String intrcQesitm;
    @Lob
    public String seQesitm;
    @Lob
    public String depositMethodQesitm;
    @Column
    public String openDe;
    @Column
    public String updateDe;
    @Column
    public String itemImage;
}
