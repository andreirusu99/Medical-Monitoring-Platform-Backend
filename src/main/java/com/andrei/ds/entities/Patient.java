package com.andrei.ds.entities;

import com.andrei.ds.DTOs.PatientDTO;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "auth_password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "record")
    private String record;

    public Patient() {
    }

    public Patient(String name, String birthDate, String gender, String password, String address, String record) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.address = address;
        this.record = record;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public String getRecord() {
        return record;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        return Objects.equals(this.name, patient.getName())
                && Objects.equals(this.birthDate, patient.getBirthDate())
                && Objects.equals(this.gender, patient.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, gender);
    }

    public Patient updateDetailsFromDTO(PatientDTO patientDTO) {
        this.password = patientDTO.getPassword();
        this.address = patientDTO.getAddress();
        this.record = patientDTO.getRecord();
        return this;
    }

}
