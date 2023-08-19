package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponseStatus;
import com.WhatIsMethIs.src.dto.medicine.PillImageDto;
import com.WhatIsMethIs.src.entity.PillImage;
import com.WhatIsMethIs.src.repository.PillImageRepository;
import com.WhatIsMethIs.utils.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PillImageService {
    private final PillImageRepository pillImageRepository;

    private final FileHandler fileHandler;

    public List<PillImageDto> addPillImage(
            List<MultipartFile> files
    ) throws BaseException {
        List<PillImageDto> pillImageDtos = new ArrayList<>();

        try {
            List<PillImageDto> list = fileHandler.parseImageInfo(files);
            System.out.println(list.isEmpty());
            if(list.isEmpty()){
                throw new BaseException(BaseResponseStatus.FILE_NOT_FOUND_EXCEPTION);
            }

            /*for(PillImageDto pillImageDto : list){
                PillImage pillImage = new PillImage();
                pillImage.setOriginalFileName(pillImageDto.getOriginalFileName());
                pillImage.setStoredFilePath(pillImageDto.getStoredFilePath());
                pillImage.setFileSize(pillImageDto.getFileSize());
                pillImage.setCreatedAt(pillImageDto.getCreatedAt());
                System.out.println(pillImage.getId());

                pillImageDtos.add(
                        PillImageDto.toDto(
                                pillImageRepository.save(
                                        pillImage
                                )
                        )
                );
            }*/
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FILE_NOT_FOUND_EXCEPTION);
        }

        return pillImageDtos;
    }
}
