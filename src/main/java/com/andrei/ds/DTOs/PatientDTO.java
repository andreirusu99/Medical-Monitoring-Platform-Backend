package com.andrei.ds.DTOs;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PatientDTO {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String birthDate;

    @NotNull
    private String gender;

    @NotNull
    private String password;

    @NotNull
    private String address;

    @NotNull
    private String record;

    public PatientDTO() {
    }

    public PatientDTO(UUID id,
                      @NotNull String name,
                      @NotNull String birthDate,
                      @NotNull String gender,
                      @NotNull String password,
                      @NotNull String address,
                      @NotNull String record) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.address = address;
        this.record = record;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getRecord() {
        return record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientDTO patientDTO = (PatientDTO) o;
        return Objects.equals(this.name, patientDTO.getName())
                && Objects.equals(this.birthDate, patientDTO.getBirthDate())
                && Objects.equals(this.gender, patientDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, gender);
    }

}
