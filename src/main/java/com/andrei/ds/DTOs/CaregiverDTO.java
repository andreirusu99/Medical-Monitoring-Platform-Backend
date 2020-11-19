package com.andrei.ds.DTOs;


import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CaregiverDTO {

    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String birthDate;

    @NonNull
    private String gender;

    @NonNull
    private String password;

    @NonNull
    private String address;

    @NonNull
    private Set<UUID> patients;

    public CaregiverDTO() {
    }

    public CaregiverDTO(UUID id,
                        @NonNull String name,
                        @NonNull String birthDate,
                        @NonNull String gender,
                        @NonNull String password,
                        @NonNull String address,
                        @NonNull Set<UUID> patients) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.address = address;
        this.patients = patients;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setBirthDate(@NonNull String birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public void setPatients(@NonNull Set<UUID> patients) {
        this.patients = patients;
    }

    public UUID getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getBirthDate() {
        return birthDate;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public Set<UUID> getPatients() {
        return patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaregiverDTO caregiverDTO = (CaregiverDTO) o;
        return Objects.equals(this.name, caregiverDTO.getName())
                && Objects.equals(this.birthDate, caregiverDTO.getBirthDate())
                && Objects.equals(this.gender, caregiverDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, gender);
    }

}
