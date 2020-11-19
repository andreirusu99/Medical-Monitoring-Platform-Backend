package com.andrei.ds.DTOs.builders;

import com.andrei.ds.DTOs.DoctorDTO;
import com.andrei.ds.entities.Doctor;

public class DoctorBuilder {

    private DoctorBuilder() {
    }

    public static DoctorDTO toDoctorDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getPassword(),
                doctor.getAddress(),
                doctor.getAge(),
                doctor.getDepartment()
        );
    }

    public static Doctor toEntity(DoctorDTO doctorDTO) {
        return new Doctor(
                doctorDTO.getName(),
                doctorDTO.getPassword(),
                doctorDTO.getAddress(),
                doctorDTO.getAge(),
                doctorDTO.getDepartment()
        );
    }
}
