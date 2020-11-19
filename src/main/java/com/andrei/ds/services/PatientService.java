package com.andrei.ds.services;

import com.andrei.ds.DTOs.PatientDTO;
import com.andrei.ds.DTOs.builders.PatientBuilder;
import com.andrei.ds.controllers.handlers.exceptions.model.AuthException;
import com.andrei.ds.controllers.handlers.exceptions.model.DuplicateResourceException;
import com.andrei.ds.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.andrei.ds.entities.Patient;
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
public class PatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> retrievePatients(UUID userId, String userType) throws AuthException {
        if (Objects.equals(userType, "doctor") || Objects.equals(userType, "caregiver")) {
            List<Patient> patientList = patientRepository.findAll();
            return patientList.stream()
                    .map(PatientBuilder::toPatientDTO)
                    .collect(Collectors.toList());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public PatientDTO retrievePatient(UUID patientId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db", patientId);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id: " + patientId);
        }
        if (Objects.equals(userType, "doctor") || Objects.equals(userType, "caregiver")
                || (Objects.equals(userType, "patient") && Objects.equals(userId.toString(), patientId.toString()))) {
            return PatientBuilder.toPatientDTO(optionalPatient.get());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID insertPatient(PatientDTO patientDTO, UUID userId, String userType) throws DuplicateResourceException, AuthException {
        Patient prospectivePatient = PatientBuilder.toEntity(patientDTO);
        Optional<Patient> existingPatient = patientRepository.findPatientByDetails(patientDTO.getName(), patientDTO.getBirthDate(), patientDTO.getGender());
        if (existingPatient.isPresent()) {
            LOGGER.error("Patient with the specified details already present!");
            throw new DuplicateResourceException(prospectivePatient.getName() + ", " + prospectivePatient.getBirthDate() + ", " + prospectivePatient.getGender());
        }
        if (Objects.equals(userType, "doctor")) {
            prospectivePatient = patientRepository.save(prospectivePatient);
            LOGGER.info("Patient with id {} was inserted in db", prospectivePatient.getId());
            return prospectivePatient.getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public PatientDTO updatePatientDetails(PatientDTO patientDTO, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        UUID patientId = patientDTO.getId();
        Optional<Patient> toUpdate = patientRepository.findById(patientId);
        if (!toUpdate.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db", patientId);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id: " + patientId);
        }
        if (Objects.equals(userType, "doctor")) {
            Patient updated = toUpdate.get().updateDetailsFromDTO(patientDTO);
            patientRepository.save(updated);
            LOGGER.info("Patient with id {} updated", patientId);
            return PatientBuilder.toPatientDTO(updated);
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID deletePatient(UUID patientId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Patient> toDelete = patientRepository.findById(patientId);
        if (!toDelete.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db", patientId);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id: " + patientId);
        }
        if (Objects.equals(userType, "doctor")) {
            patientRepository.delete(toDelete.get());
            return toDelete.get().getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

}
