package com.andrei.ds.repositories;

import com.andrei.ds.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    @Query(value = "SELECT a " +
            "FROM Activity a " +
            "WHERE a.activityName = :activityName " +
            "AND a.patientId = :patientId " +
            "AND a.startTime = :startTime " +
            "AND a.endTime = :endTime"
    )
    Optional<Activity> findActivityByDetails(
            @Param("patientId") UUID patientId,
            @Param("activityName") String activityName,
            @Param("startTime") Long startTime,
            @Param("endTime") Long endTime
    );
}
