package com.andrei.ds.DTOs.builders;

import com.andrei.ds.DTOs.TreatmentDTO;
import com.andrei.ds.entities.Treatment;

public class TreatmentBuilder {

    public TreatmentBuilder() {
    }

    public static TreatmentDTO toTreatmentDTO(Treatment treatment) {
        return new TreatmentDTO(
                treatment.getId(),
                treatment.getPatientId(),
                treatment.getStartDate(),
                treatment.getEndDate(),
                treatment.getDosage()
        );
    }

    public static Treatment toEntity(TreatmentDTO treatmentDTO) {
        return new Treatment(
                treatmentDTO.getPatientId(),
                treatmentDTO.getStartDate(),
                treatmentDTO.getEndDate(),
                treatmentDTO.getDosage()
        );
    }

}
