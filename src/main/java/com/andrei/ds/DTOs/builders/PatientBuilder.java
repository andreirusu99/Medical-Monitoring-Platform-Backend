package com.andrei.ds.DTOs.builders;

import com.andrei.ds.DTOs.PatientDTO;
import com.andrei.ds.entities.Patient;

public class PatientBuilder {

    private PatientBuilder() {
    }

    public static PatientDTO toPatientDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getBirthDate(),
                patient.getGender(),
                patient.getPassword(),
                patient.getAddress(),
                patient.getRecord()
        );
    }

    public static Patient toEntity(PatientDTO patientDTO) {
        return new Patient(
                patientDTO.getName(),
                patientDTO.getBirthDate(),
                patientDTO.getGender(),
                patientDTO.getPassword(),
                patientDTO.getAddress(),
                patientDTO.getRecord()
        );
    }
}
