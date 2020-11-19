package com.andrei.ds.services;

import com.andrei.ds.DTOs.CaregiverDTO;
import com.andrei.ds.DTOs.builders.CaregiverBuilder;
import com.andrei.ds.controllers.handlers.exceptions.model.AuthException;
import com.andrei.ds.controllers.handlers.exceptions.model.DuplicateResourceException;
import com.andrei.ds.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.andrei.ds.entities.Caregiver;
import com.andrei.ds.entities.Patient;
import com.andrei.ds.repositories.CaregiverRepository;
import com.andrei.ds.repositories.PatientRepository;
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
public class CaregiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaregiverService.class);

    private final CaregiverRepository caregiverRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public CaregiverService(CaregiverRepository caregiverRepository, PatientRepository patientRepository) {
        this.caregiverRepository = caregiverRepository;
        this.patientRepository = patientRepository;
    }

    public List<CaregiverDTO> retrieveAllCaregivers(UUID userId, String userType) throws AuthException {
        if (Objects.equals(userType, "doctor")) {
            List<Caregiver> caregivers = caregiverRepository.findAll();
            return caregivers.stream()
                    .map(CaregiverBuilder::toCaregiverDTO)
                    .collect(Collectors.toList());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public CaregiverDTO retrieveCaregiver(UUID caregiverId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Caregiver> optionalCaregiver = caregiverRepository.findById(caregiverId);
        if (!optionalCaregiver.isPresent()) {
            LOGGER.error("Caregiver with ID {} does not exist!", caregiverId);
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id: " + caregiverId);
        }
        if (Objects.equals(userType, "doctor") || (Objects.equals(userType, "caregiver") && Objects.equals(userId.toString(), caregiverId.toString()))) {
            return CaregiverBuilder.toCaregiverDTO(optionalCaregiver.get());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID insertCaregiver(CaregiverDTO caregiverDTO, UUID userId, String userType) throws ResourceNotFoundException, DuplicateResourceException, AuthException {
        caregiverDTO.getPatients().forEach(uuid -> {
            Optional<Patient> optionalPatient = patientRepository.findById(uuid);
            if (!optionalPatient.isPresent()) {
                LOGGER.error("Patient with ID {} does not exist!", uuid);
                throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with ID " + uuid);
            }
        });
        if (Objects.equals(userType, "doctor")) {
            Caregiver prospectiveCaregiver = CaregiverBuilder.toEntity(caregiverDTO);
            Optional<Caregiver> existingCaregiver = caregiverRepository.findCaregiverByDetails(prospectiveCaregiver.getName(), prospectiveCaregiver.getBirthDate(), prospectiveCaregiver.getGender());
            if (existingCaregiver.isPresent()) {
                LOGGER.error("Caregiver with the specified details already present!");
                throw new DuplicateResourceException(prospectiveCaregiver.getName() + ", " + prospectiveCaregiver.getBirthDate() + ", " + prospectiveCaregiver.getGender());
            }
            prospectiveCaregiver = caregiverRepository.save(prospectiveCaregiver);
            LOGGER.info("Caregiver with id {} was inserted in db", prospectiveCaregiver.getId());
            return prospectiveCaregiver.getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public CaregiverDTO updateCaregiver(CaregiverDTO caregiverDTO, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Caregiver> toUpdate = caregiverRepository.findById(caregiverDTO.getId());
        if (!toUpdate.isPresent()) {
            LOGGER.error("Caregiver with ID {} does not exist!", caregiverDTO.getId());
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id: " + caregiverDTO.getId());
        }
        if (Objects.equals(userType, "doctor")) {
            caregiverDTO.getPatients().forEach(uuid -> {
                Optional<Patient> optionalPatient = patientRepository.findById(uuid);
                if (!optionalPatient.isPresent()) {
                    LOGGER.error("Patient with ID {} does not exist!", uuid);
                    throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with ID " + uuid);
                }
            });
            Caregiver updated = toUpdate.get().updateDetailsFromDTO(caregiverDTO);
            caregiverRepository.save(updated);
            LOGGER.info("Caregiver with id {} updated", caregiverDTO.getId());
            return CaregiverBuilder.toCaregiverDTO(updated);
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID deleteCaregiver(UUID caregiverId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Caregiver> toDelete = caregiverRepository.findById(caregiverId);
        if (!toDelete.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db!", caregiverId);
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + "with id: " + caregiverId);
        }
        if (Objects.equals(userType, "doctor")) {
            caregiverRepository.delete(toDelete.get());
            return toDelete.get().getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

}
