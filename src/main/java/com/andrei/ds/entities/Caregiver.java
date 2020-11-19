package com.andrei.ds.entities;

import com.andrei.ds.DTOs.CaregiverDTO;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Caregiver {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "auth_password")
    private String password;

    @Column(name = "address")
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UUID> patients;

    public Caregiver() {
    }

    public Caregiver(String name,
                     String birthDate,
                     String gender,
                     String password,
                     String address,
                     Set<UUID> patients) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.address = address;
        this.patients = patients;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public void setPatients(Set<UUID> patients) {
        this.patients = patients;
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
        Caregiver caregiver = (Caregiver) o;
        return
                Objects.equals(this.name, caregiver.getName())
                        && Objects.equals(this.birthDate, caregiver.getBirthDate())
                        && Objects.equals(this.gender, caregiver.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, gender);
    }

    public Caregiver updateDetailsFromDTO(CaregiverDTO caregiverDTO) {
        this.password = caregiverDTO.getPassword();
        this.address = caregiverDTO.getAddress();
        this.patients = caregiverDTO.getPatients();
        return this;
    }

}
