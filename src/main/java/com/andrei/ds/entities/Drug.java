package com.andrei.ds.entities;

import com.andrei.ds.DTOs.DrugDTO;
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
public class Drug implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "side_effects", nullable = false)
    private String sideEffects;

    @Column(name = "dosage", nullable = false)
    private int dosage;

    public Drug() {
    }

    public Drug(String name, String sideEffects, int dosage) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Drug drug = (Drug) o;
        return Objects.equals(name, drug.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Drug updateDetailsFromDTO(DrugDTO drugDTO) {
        this.sideEffects = drugDTO.getSideEffects();
        this.dosage = drugDTO.getDosage();
        return this;
    }
}
