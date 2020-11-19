package com.andrei.ds.services;

import com.andrei.ds.DTOs.DoctorDTO;
import com.andrei.ds.DTOs.builders.DoctorBuilder;
import com.andrei.ds.controllers.handlers.exceptions.model.AuthException;
import com.andrei.ds.controllers.handlers.exceptions.model.DuplicateResourceException;
import com.andrei.ds.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.andrei.ds.entities.Doctor;
import com.andrei.ds.repositories.DoctorRepository;
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
public class DoctorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDTO> retrieveDoctors() {
        List<Doctor> doctorList = doctorRepository.findAll();
        return doctorList.stream()
                .map(DoctorBuilder::toDoctorDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO retrieveDoctorById(UUID doctorId, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (!doctorOptional.isPresent()) {
            LOGGER.error("Doctor with id {} was not found in db", doctorId);
            throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " with id: " + doctorId);
        }
        if (Objects.equals(userType, "doctor") && Objects.equals(userId.toString(), doctorId.toString())) {
            return DoctorBuilder.toDoctorDTO(doctorOptional.get());
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

    public UUID insertDoctor(DoctorDTO doctorDTO) throws DuplicateResourceException {
        Doctor prospectiveDoctor = DoctorBuilder.toEntity(doctorDTO);
        Optional<Doctor> existingDoctor = doctorRepository.findDoctorByDetails(doctorDTO.getName(), doctorDTO.getAge());
        if (existingDoctor.isPresent()) {
            LOGGER.error("Doctor with the specified details already present!");
            throw new DuplicateResourceException(prospectiveDoctor.getName() + ", " + prospectiveDoctor.getAge());
        }
        prospectiveDoctor = doctorRepository.save(prospectiveDoctor);
        LOGGER.info("Doctor with id {} was inserted in db", prospectiveDoctor.getId());
        return prospectiveDoctor.getId();
    }

    public DoctorDTO updateDoctorDetails(DoctorDTO doctorDTO, UUID userId, String userType) throws ResourceNotFoundException, AuthException {
        UUID doctorId = doctorDTO.getId();
        Optional<Doctor> toUpdate = doctorRepository.findById(doctorId);
        if (!toUpdate.isPresent()) {
            LOGGER.error("Doctor with id {} was not found in db", doctorId);
            throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " with id: " + doctorId);
        }
        if (Objects.equals(userType, "doctor") && Objects.equals(userId.toString(), doctorId.toString())) {
            Doctor updated = toUpdate.get().updateDetailsFromDTO(doctorDTO);
            doctorRepository.save(updated);
            LOGGER.info("Doctor with id {} updated", doctorId);
            return DoctorBuilder.toDoctorDTO(updated);
        }
        LOGGER.error("User with id {} not authorized!", userId);
        throw new AuthException("User with id: " + userId);
    }

}
