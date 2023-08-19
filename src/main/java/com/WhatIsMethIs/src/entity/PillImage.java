package com.WhatIsMethIs.src.entity;

import com.WhatIsMethIs.src.dto.medicine.PillImageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pill_image")
@NoArgsConstructor
@Schema(description = "식별 요청 이미지")
public class PillImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "original_file_name")
    @NotEmpty
    private String originalFileName;
    @Column(name = "stored_file_path")
    @NotEmpty
    private String storedFilePath;
    @Column(name = "file_size")
    private long fileSize;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static PillImage toEntity(PillImageDto pillImageDto){
        PillImage pillImage = new PillImage();

        pillImage.id = pillImageDto.getId();
        if(pillImageDto.getOriginalFileName() != null){
            pillImage.originalFileName = pillImageDto.getOriginalFileName();
        }
        if(pillImageDto.getStoredFilePath() != null){
            pillImage.storedFilePath = pillImageDto.getStoredFilePath();
        }
        pillImage.fileSize = pillImageDto.getFileSize();
        if(pillImageDto.getCreatedAt() != null){
            pillImage.createdAt = pillImageDto.getCreatedAt();
        }

        return pillImage;
    }
}
