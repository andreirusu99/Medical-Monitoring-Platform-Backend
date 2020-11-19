package com.andrei.ds.entities;

import com.andrei.ds.DTOs.TreatmentDTO;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Treatment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "patient_id")
    @Type(type = "uuid-binary")
    private UUID patientId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    /**
     * The list of associated drugs and dosages of a treatment
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Integer> dosage;

    public Treatment() {
    }

    public Treatment(UUID patientId,
                     String startDate,
                     String endDate,
                     Map<String, Integer> dosage) {
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDosage(Map<String, Integer> medication) {
        this.dosage = medication;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

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
        Treatment treatment = (Treatment) o;
        return Objects.equals(this.patientId, treatment.getPatientId())
                && Objects.equals(this.startDate, treatment.getStartDate())
                && Objects.equals(this.endDate, treatment.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, startDate, endDate);
    }

    public Treatment updateDetailsFromDTO(TreatmentDTO treatmentDTO) {
        this.startDate = treatmentDTO.getStartDate();
        this.endDate = treatmentDTO.getEndDate();
        this.dosage = treatmentDTO.getDosage();
        return this;
    }

}
