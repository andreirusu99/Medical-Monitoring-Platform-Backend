package com.andrei.ds.repositories;

import com.andrei.ds.entities.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreatmentRepository extends JpaRepository<Treatment, UUID> {

    @Query(value = "SELECT t " +
            "FROM Treatment t " +
            "JOIN Patient p ON p.id = t.patientId " +
            "WHERE p.id = :patientId "
    )
    List<Treatment> findTreatmentsByPatientId(
            @Param("patientId") UUID patientId
    );

    // for treatment updates
    @Query(value = "SELECT t " +
            "FROM Treatment t " +
            "JOIN Patient p ON p.id = t.patientId " +
            "WHERE p.id = :patientId " +
            "AND t.startDate = :startDate " +
            "AND t.endDate = :endDate  "
    )
    Optional<Treatment> findTreatmentByDetails(
            @Param("patientId") UUID patientId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
