package com.andrei.ds.repositories;

import com.andrei.ds.entities.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CaregiverRepository extends JpaRepository<Caregiver, UUID> {

    @Query(value = "SELECT c " +
            "FROM Caregiver c " +
            "WHERE c.name = :name " +
            "AND c.birthDate = :birthDate " +
            "AND c.gender = :gender "
    )
    Optional<Caregiver> findCaregiverByDetails(
            @Param("name") String name,
            @Param("birthDate") String birthDate,
            @Param("gender") String gender
    );

}
