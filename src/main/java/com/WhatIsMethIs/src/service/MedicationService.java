package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.src.dto.medication.MedicationResDto.*;
import com.WhatIsMethIs.src.entity.Medication;
import com.WhatIsMethIs.config.exception.medication.NotFoundMedicationIdException;
import com.WhatIsMethIs.src.repository.MedicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicationService {

    private final MedicationRepository medicationRepository;

    //index로 복약정보 조회
    public MedicationInfoRes getMedicationWithIndex(Long index){
        Medication medication = medicationRepository.findById(index).orElseThrow(NotFoundMedicationIdException::new);

        return MedicationInfoRes.builder()
                .medication(medication)
                .build();
    }


}
