package com.andrei.ds.DTOs;


import com.andrei.ds.DTOs.validators.annotations.AgeLimit;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class DoctorDTO {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String address;

    @AgeLimit(limit = 18)
    private int age;

    @NotNull
    private String department;


    public DoctorDTO() {
    }

    public DoctorDTO(UUID id,
                     @NotNull String name,
                     @NotNull String password,
                     @NotNull String address,
                     int age,
                     @NotNull String department) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.age = age;
        this.department = department;
    }

    public DoctorDTO(@NotNull String name,
                     @NotNull String password,
                     @NotNull String address,
                     int age,
                     @NotNull String department) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.age = age;
        this.department = department;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String auth_password) {
        this.password = auth_password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DoctorDTO doctorDTO = (DoctorDTO) o;
        return age == doctorDTO.getAge() &&
                Objects.equals(name, doctorDTO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
