package com.andrei.ds.repositories;

import com.andrei.ds.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    @Query(value = "SELECT doc " +
            "FROM Doctor doc " +
            "WHERE doc.name = :name " +
            "AND doc.age = :age  "
    )
    Optional<Doctor> findDoctorByDetails(@Param("name") String name, @Param("age") int age);

}
