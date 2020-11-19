package com.andrei.ds.DTOs;


import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TreatmentDTO {

    private UUID id;

    @NonNull
    private UUID patientId;

    @NonNull
    private String startDate;

    @NonNull
    private String endDate;

    @NonNull
    private Map<String, Integer> dosage;

    public TreatmentDTO() {
    }

    public TreatmentDTO(UUID id,
                        @NonNull UUID patientId,
                        @NonNull String startDate,
                        @NonNull String endDate,
                        @NonNull Map<String, Integer> dosage) {
        this.id = id;
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPatientId(@NonNull UUID patientId) {
        this.patientId = patientId;
    }

    public void setStartDate(@NonNull String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@NonNull String endDate) {
        this.endDate = endDate;
    }

    public void setDosage(@NonNull Map<String, Integer> dosage) {
        this.dosage = dosage;
    }

    public UUID getId() {
        return id;
    }

    @NonNull
    public UUID getPatientId() {
        return patientId;
    }

    @NonNull
    public String getStartDate() {
        return startDate;
    }

    @NonNull
    public String getEndDate() {
        return endDate;
    }

    @NonNull
    public Map<String, Integer> getDosage() {
        return dosage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreatmentDTO treatmentDTO = (TreatmentDTO) o;
        return Objects.equals(this.patientId, treatmentDTO.getPatientId())
                && Objects.equals(this.startDate, treatmentDTO.getStartDate())
                && Objects.equals(this.endDate, treatmentDTO.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, startDate, endDate);
    }


}
