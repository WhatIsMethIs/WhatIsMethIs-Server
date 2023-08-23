package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.exception.medication.NotMyMedicationException;
import com.WhatIsMethIs.config.exception.user.NotFoundUserIdException;
import com.WhatIsMethIs.src.dto.medication.MedicationResDto.*;
import com.WhatIsMethIs.src.entity.Medication;
import com.WhatIsMethIs.config.exception.medication.NotFoundMedicationIdException;
import com.WhatIsMethIs.src.entity.User;
import com.WhatIsMethIs.src.repository.MedicationRepository;
import com.WhatIsMethIs.src.repository.UserRepository;
import com.WhatIsMethIs.utils.TokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicationService {

    private final TokenUtils tokenUtils;

    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;

    //index로 복약정보 조회
    public MedicationInfoRes getMedicationWithIndex(Long index) throws BaseException {
        int userId= tokenUtils.getUserId();
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserIdException::new);

        Medication medication = medicationRepository.findById(index).orElseThrow(NotFoundMedicationIdException::new);

        if(!medication.getUserId().equals(user)){
            throw new NotMyMedicationException();
        }

        return MedicationInfoRes.builder()
                .medication(medication)
                .build();
    }


}
