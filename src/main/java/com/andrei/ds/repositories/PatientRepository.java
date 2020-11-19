package com.andrei.ds.repositories;

import com.andrei.ds.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface PatientRepository extends JpaRepository<Patient, UUID> {
    @Query(value = "SELECT p " +
            "FROM Patient p " +
            "WHERE p.name = :name " +
            "AND p.birthDate = :birthDate " +
            "AND p.gender = :gender  "
    )
    Optional<Patient> findPatientByDetails(
            @Param("name") String name,
            @Param("birthDate") String birthDate,
            @Param("gender") String gender
    );

}
