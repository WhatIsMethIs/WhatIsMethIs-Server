package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.src.entity.Medicine;
import com.WhatIsMethIs.src.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.WhatIsMethIs.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    public List<Medicine> getMedicines() throws BaseException{
        try{
            return medicineRepository.findAll();
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
