package com.andrei.ds.services;

import com.andrei.ds.DTOs.DrugDTO;
import com.andrei.ds.DTOs.builders.DrugBuilder;
import com.andrei.ds.controllers.handlers.exceptions.model.AuthException;
import com.andrei.ds.controllers.handlers.exceptions.model.DuplicateResourceException;
import com.andrei.ds.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.andrei.ds.entities.Drug;
import com.andrei.ds.repositories.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DrugService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DrugService.class);
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public List<DrugDTO> retrieveDrugs(UUID userId, String userType) throws AuthException {
        if (Objects.equals(userType, "doctor")) {
            List<Drug> drugList = drugRepository.findAll();
            return drugList.stream()
                    .map(DrugBuilder::toDrugDTO)
                    .collect(Collectors.toList());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public DrugDTO retrieveDrug(String drugName, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Drug> optionalDrug = drugRepository.findDrugByName(drugName);
        if (!optionalDrug.isPresent()) {
            LOGGER.error("Drug with id {} was not found in db", drugName);
            throw new ResourceNotFoundException(Drug.class.getSimpleName() + " " + drugName);
        }
        if (Objects.equals(userType, "doctor")) {
            return DrugBuilder.toDrugDTO(optionalDrug.get());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID insertDrug(DrugDTO drugDTO, UUID userId, String userType) throws DuplicateResourceException, AuthException {
        Drug prospectiveDrug = DrugBuilder.toEntity(drugDTO);
        Optional<Drug> existingDrug = drugRepository.findDrugByName(drugDTO.getName());
        if (existingDrug.isPresent()) {
            LOGGER.error("Drug with the specified details already present!");
            throw new DuplicateResourceException(prospectiveDrug.getName());
        }
        if (Objects.equals(userType, "doctor")) {
            prospectiveDrug = drugRepository.save(prospectiveDrug);
            LOGGER.info("Drug with id {} was inserted in db", prospectiveDrug.getId());
            return prospectiveDrug.getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public DrugDTO updateDrugDetails(DrugDTO drugDTO, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        String drugName = drugDTO.getName();
        Optional<Drug> toUpdate = drugRepository.findDrugByName(drugName);
        if (!toUpdate.isPresent()) {
            LOGGER.error("Drug \"{}\" was not found in db", drugName);
            throw new ResourceNotFoundException(Drug.class.getSimpleName() + " with id: " + drugName);
        }
        if (Objects.equals(userType, "doctor")) {
            Drug updated = toUpdate.get().updateDetailsFromDTO(drugDTO);
            drugRepository.save(updated);
            LOGGER.info("Drug \"{}\" updated", drugName);
            return DrugBuilder.toDrugDTO(updated);
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID deleteDrug(String drugName, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Drug> toDelete = drugRepository.findDrugByName(drugName);
        if (!toDelete.isPresent()) {
            LOGGER.error("Drug \"{}\" was not found in db", drugName);
            throw new ResourceNotFoundException(Drug.class.getSimpleName() + " with id: " + drugName);
        }
        if (Objects.equals(userType, "doctor")) {
            drugRepository.delete(toDelete.get());
            return toDelete.get().getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

}
