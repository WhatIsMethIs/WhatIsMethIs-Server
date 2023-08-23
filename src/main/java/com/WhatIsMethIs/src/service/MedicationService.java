package com.WhatIsMethIs.src.service;

import com.WhatIsMethIs.config.BaseException;
import com.WhatIsMethIs.config.exception.medication.NotMyMedicationException;
import com.WhatIsMethIs.config.exception.user.NotFoundUserIdException;
import com.WhatIsMethIs.src.dto.medication.MedicationReqDto.*;
import com.WhatIsMethIs.src.dto.medication.MedicationResDto.*;
import com.WhatIsMethIs.src.entity.Medication;
import com.WhatIsMethIs.config.exception.medication.NotFoundMedicationIdException;
import com.WhatIsMethIs.src.entity.Medicine;
import com.WhatIsMethIs.src.entity.User;
import com.WhatIsMethIs.src.repository.MedicationRepository;
import com.WhatIsMethIs.src.repository.MedicineRepository;
import com.WhatIsMethIs.src.repository.UserRepository;
import com.WhatIsMethIs.utils.TokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MedicationService {

    private final TokenUtils tokenUtils;

    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;
    private final MedicineRepository medicineRepository;

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

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

    //복약정보 등록
    public MedicationIdRes createMedication(MedicationInfoReq medicationInfo) throws BaseException {
        int userId= tokenUtils.getUserId();
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserIdException::new);

        Medicine medicine = medicineRepository.findById(medicationInfo.getMedicineId()).orElseThrow();

        Medication medication = Medication.builder()
                .userId(user)
                .medicineId(medicine)
                .medicineName(medicationInfo.getMedicineName())
                .medicineImage(medicationInfo.getMedicineImage())
                .takeStartDate(medicationInfo.getTakeStartDate())
                .takeEndDate(medicationInfo.getTakeEndDate())
                .takeMealTime(medicationInfo.getTakeMealTime())
                .takeBeforeAfter(medicationInfo.getTakeBeforeAfter())
                .takeCapacity(medicationInfo.getTakeCapacity())
                .takeCycle(medicationInfo.getTakeCycle())
                .notificationTime(medicationInfo.getNotificationTime())
                .description(medicationInfo.getDescription())
                .build();

        Medication medEntity = medicationRepository.save(medication);
        return new MedicationIdRes(medEntity.getId());
    }

    //복약 정보 전체 조회 - 페이징 (10개 단위) -> 후에 수정될 수 있음
    public MedicationShortInfoListRes getMedicationShortInfoList(int page) throws BaseException {
        int userId= tokenUtils.getUserId();
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserIdException::new);

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Medication> medications = medicationRepository.findByUserIdOrderByIdDesc(user, pageRequest);
        log.warn(LOG_FORMAT, medications.stream().count());

        List<MedicationShortInfoRes> medicationShortInfos = medications.stream()
                .map(medication -> new MedicationShortInfoRes(medication.getMedicineName(),
                        medication.getMedicineImage(), medication.getTakeMealTime(), medication.getTakeBeforeAfter(),
                        medication.getTakeCapacity()))
                .collect(Collectors.toList());

        return new MedicationShortInfoListRes(medicationShortInfos, medications.getTotalPages());
    }



}
