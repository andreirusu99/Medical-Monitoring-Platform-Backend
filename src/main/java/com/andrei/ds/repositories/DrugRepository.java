package com.andrei.ds.repositories;

import com.andrei.ds.entities.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DrugRepository extends JpaRepository<Drug, UUID> {

    @Query(value = "SELECT d " +
            "FROM Drug d " +
            "WHERE d.name = :name "
    )
    Optional<Drug> findDrugByName(@Param("name") String name);


}
