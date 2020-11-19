package com.andrei.ds.DTOs.builders;

import com.andrei.ds.DTOs.DrugDTO;
import com.andrei.ds.entities.Drug;

public class DrugBuilder {

    private DrugBuilder() {

    }

    public static DrugDTO toDrugDTO(Drug drug) {
        return new DrugDTO(
                drug.getId(),
                drug.getName(),
                drug.getSideEffects(),
                drug.getDosage()
        );
    }

    public static Drug toEntity(DrugDTO drugDTO) {
        return new Drug(
                drugDTO.getName(),
                drugDTO.getSideEffects(),
                drugDTO.getDosage()
        );
    }

}
