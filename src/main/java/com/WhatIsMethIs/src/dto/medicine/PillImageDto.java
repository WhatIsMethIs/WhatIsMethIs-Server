package com.WhatIsMethIs.src.dto.medicine;

import com.WhatIsMethIs.src.entity.PillImage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PillImageDto {
    private int id;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private LocalDateTime createdAt;

    public static PillImageDto toDto(PillImage pillImage){
        PillImageDto pillImageDto = new PillImageDto();

        pillImageDto.id = pillImage.getId();
        if(pillImage.getOriginalFileName() != null){
            pillImageDto.originalFileName = pillImage.getOriginalFileName();
        }
        if(pillImage.getStoredFilePath() != null){
            pillImageDto.storedFilePath = pillImage.getStoredFilePath();
        }
        pillImageDto.fileSize = pillImage.getFileSize();
        if(pillImage.getCreatedAt() != null){
            pillImageDto.createdAt = pillImage.getCreatedAt();
        }

        return pillImageDto;
    }
}
