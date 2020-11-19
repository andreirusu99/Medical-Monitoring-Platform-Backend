package com.andrei.ds.DTOs;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class DrugDTO {
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String sideEffects;

    @NotNull
    private int dosage;

    public DrugDTO() {
    }

    public DrugDTO(UUID id,
                   @NotNull String name,
                   @NotNull String sideEffects,
                   @NotNull int dosage) {
        this.id = id;
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
    }

    public DrugDTO(@NotNull String name,
                   @NotNull String sideEffects,
                   @NotNull int dosage) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSideEffects(String side_effects) {
        this.sideEffects = side_effects;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public int getDosage() {
        return dosage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrugDTO drugDTO = (DrugDTO) o;
        return Objects.equals(name, drugDTO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
