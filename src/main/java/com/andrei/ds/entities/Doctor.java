package com.andrei.ds.entities;

import com.andrei.ds.DTOs.DoctorDTO;
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
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "auth_password", nullable = false)
    private String password;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "department", nullable = false)
    private String department;

    public Doctor() {
    }

    public Doctor(
            String name,
            String password,
            String address,
            int age,
            String department) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public void setPassword(String auth_password) {
        this.password = auth_password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doctor doctor = (Doctor) o;
        return age == doctor.getAge() &&
                Objects.equals(name, doctor.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    public Doctor updateDetailsFromDTO(DoctorDTO doctorDTO) {
        this.password = doctorDTO.getPassword();
        this.address = doctorDTO.getAddress();
        this.age = doctorDTO.getAge();
        this.department = doctorDTO.getDepartment();
        return this;
    }

}
