package com.WhatIsMethIs.utils;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.BaseResponseStatus;
import com.WhatIsMethIs.src.dto.medicine.PillImageDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {
    public List<PillImageDto> parseImageInfo(
            List<MultipartFile> multipartFiles
    ) throws IOException, BaseException {
        // 반환을 할 파일 리스트
        List<PillImageDto> fileList = new ArrayList<>();

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if(multipartFiles.isEmpty()){
            return fileList;
        }

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용)
        String absolutePath = new File("").getAbsolutePath() + "/";

        // 경로를 지정하고 이미지 저장
        String path = "images/" + current_date;
        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        // 파일 처리
        for (MultipartFile multipartFile : multipartFiles){
            // 파일이 비어 있지 않을 때 작업을 시작
            if(!multipartFile.isEmpty()){
                // jpeg, png, gif 파일들만 받아서 처리
                String contentType = multipartFile.getContentType();
                String originalFileExtension;
                // 확장자 명이 없으면 이 파일은 잘 못 된 것
                if (ObjectUtils.isEmpty(contentType)){
                    break;
                }
                else{
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                    }
                    // 다른 파일 명이면 break
                    else{
                        break;
                    }
                }
                // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
                String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
                // 생성 후 리스트에 추가
                PillImageDto pillImageDto = new PillImageDto();
                pillImageDto.setOriginalFileName(multipartFile.getOriginalFilename());
                pillImageDto.setStoredFilePath(path + "/" + new_file_name);
                pillImageDto.setFileSize(multipartFile.getSize());
                fileList.add(pillImageDto);

                // 저장된 파일로 변경하여 이를 보여주기 위함
                file = new File(absolutePath + path + "/" + new_file_name);
                System.out.println(absolutePath + path + "/" + new_file_name);
                try{
                    multipartFile.transferTo(file);
                }
                catch (Exception e){
                    throw new BaseException(BaseResponseStatus.FILEHANDLER_FUNC_TRANFER_TO_EXCEPTION);
                }
            }
        }
        System.out.println("debug at FileHandler");

        return fileList;
    }
}
