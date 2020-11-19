package com.andrei.ds.services;

import com.andrei.ds.DTOs.TreatmentDTO;
import com.andrei.ds.DTOs.builders.TreatmentBuilder;
import com.andrei.ds.controllers.handlers.exceptions.model.AuthException;
import com.andrei.ds.controllers.handlers.exceptions.model.DuplicateResourceException;
import com.andrei.ds.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.andrei.ds.entities.Drug;
import com.andrei.ds.entities.Patient;
import com.andrei.ds.entities.Treatment;
import com.andrei.ds.repositories.DrugRepository;
import com.andrei.ds.repositories.PatientRepository;
import com.andrei.ds.repositories.TreatmentRepository;
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
public class TreatmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreatmentService.class);

    private final TreatmentRepository treatmentRepository;
    private final PatientRepository patientRepository;
    private final DrugRepository drugRepository;

    @Autowired
    public TreatmentService(TreatmentRepository treatmentRepository, PatientRepository patientRepository, DrugRepository drugRepository) {
        this.treatmentRepository = treatmentRepository;
        this.patientRepository = patientRepository;
        this.drugRepository = drugRepository;
    }

    public List<TreatmentDTO> retrievePatientTreatments(UUID patientId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            LOGGER.error("Patient with ID {} does not exist!", patientId);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with ID " + patientId);
        }
        if (Objects.equals(userType, "doctor") || Objects.equals(userType, "caregiver") || Objects.equals(userId.toString(), patientId.toString())) {
            List<Treatment> treatments = treatmentRepository.findTreatmentsByPatientId(patientId);
            return treatments.stream()
                    .map(TreatmentBuilder::toTreatmentDTO)
                    .collect(Collectors.toList());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);

    }

    public TreatmentDTO retrieveTreatment(UUID treatmentId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Treatment> optionalTreatment = treatmentRepository.findById(treatmentId);
        if (!optionalTreatment.isPresent()) {
            LOGGER.error("Treatment with id {} was not found in db", treatmentId);
            throw new ResourceNotFoundException(Treatment.class.getSimpleName() + " with id: " + treatmentId);
        }
        if (Objects.equals(userType, "doctor") || Objects.equals(userType, "caregiver") || Objects.equals(userType, "patient")) {
            return TreatmentBuilder.toTreatmentDTO(optionalTreatment.get());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID addTreatment(TreatmentDTO treatmentDTO, UUID userId, String userType) throws ResourceNotFoundException, DuplicateResourceException, AuthException {
        UUID patientId = treatmentDTO.getPatientId();
        // check patient
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            LOGGER.error("Patient with ID {} does not exist!", patientId);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with ID " + patientId);
        }
        if (Objects.equals(userType, "doctor")) {
            // check drugs
            treatmentDTO.getDosage().forEach((drugName, dosage) -> {
                Optional<Drug> optionalDrug = drugRepository.findDrugByName(drugName);
                if (!optionalDrug.isPresent()) {
                    LOGGER.error("Drug {} does not exist!", drugName);
                    throw new ResourceNotFoundException(Drug.class.getSimpleName() + " " + drugName);
                }
            });

            Treatment prospectiveTreatment = TreatmentBuilder.toEntity(treatmentDTO);
            Optional<Treatment> existingTreatment = treatmentRepository.findTreatmentByDetails(treatmentDTO.getPatientId(), treatmentDTO.getStartDate(), treatmentDTO.getEndDate());
            if (existingTreatment.isPresent()) {
                LOGGER.error("Treatment with the specified details already present!");
                throw new DuplicateResourceException(prospectiveTreatment.getPatientId() + ", " + prospectiveTreatment.getStartDate() + ", " + prospectiveTreatment.getEndDate());
            }
            prospectiveTreatment = treatmentRepository.save(prospectiveTreatment);
            LOGGER.info("Treatment with id {} was inserted in db", prospectiveTreatment.getId());
            return prospectiveTreatment.getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public TreatmentDTO updateTreatmentDetails(TreatmentDTO treatmentDTO, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        UUID patientId = treatmentDTO.getPatientId();
        // check patient
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            LOGGER.error("Patient with ID {} does not exist!", patientId);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with ID " + patientId);
        }
        if (Objects.equals(userType, "doctor")) {
            // check drugs
            treatmentDTO.getDosage().forEach((drugName, dosage) -> {
                Optional<Drug> optionalDrug = drugRepository.findDrugByName(drugName);
                if (!optionalDrug.isPresent()) {
                    LOGGER.error("Drug {} does not exist!", drugName);
                    throw new ResourceNotFoundException(Drug.class.getSimpleName() + " " + drugName);
                }
            });
            UUID treatmentId = treatmentDTO.getId();
            Optional<Treatment> toUpdate = treatmentRepository.findById(treatmentId);
            if (!toUpdate.isPresent()) {
                LOGGER.error("Treatment with id {} was not found in db", treatmentId);
                throw new ResourceNotFoundException(Treatment.class.getSimpleName() + " with id: " + treatmentId);
            }
            Treatment updated = toUpdate.get().updateDetailsFromDTO(treatmentDTO);
            treatmentRepository.save(updated);
            LOGGER.info("Treatment with id {} updated", treatmentId);
            return TreatmentBuilder.toTreatmentDTO(updated);
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID deleteTreatment(UUID treatmentId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Treatment> toDelete = treatmentRepository.findById(treatmentId);
        if (!toDelete.isPresent()) {
            LOGGER.error("Treatment with id {} was not found in db", treatmentId);
            throw new ResourceNotFoundException(Drug.class.getSimpleName() + " with id: " + treatmentId);
        }
        if (Objects.equals(userType, "doctor")) {
            treatmentRepository.delete(toDelete.get());
            return toDelete.get().getId();
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

}
