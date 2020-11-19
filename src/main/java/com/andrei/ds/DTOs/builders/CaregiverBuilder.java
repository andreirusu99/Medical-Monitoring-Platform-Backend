package com.andrei.ds.DTOs.builders;

import com.andrei.ds.DTOs.CaregiverDTO;
import com.andrei.ds.entities.Caregiver;

public class CaregiverBuilder {

    public CaregiverBuilder() {
    }

    public static CaregiverDTO toCaregiverDTO(Caregiver caregiver) {
        return new CaregiverDTO(
                caregiver.getId(),
                caregiver.getName(),
                caregiver.getBirthDate(),
                caregiver.getGender(),
                caregiver.getPassword(),
                caregiver.getAddress(),
                caregiver.getPatients()
        );
    }

    public static Caregiver toEntity(CaregiverDTO caregiverDTO) {
        return new Caregiver(
                caregiverDTO.getName(),
                caregiverDTO.getBirthDate(),
                caregiverDTO.getGender(),
                caregiverDTO.getPassword(),
                caregiverDTO.getAddress(),
                caregiverDTO.getPatients()
        );
    }

}
