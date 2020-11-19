package com.andrei.ds.services;

import com.andrei.ds.DTOs.LoginDTO;
import com.andrei.ds.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.andrei.ds.entities.Caregiver;
import com.andrei.ds.entities.Doctor;
import com.andrei.ds.entities.Patient;
import com.andrei.ds.repositories.CaregiverRepository;
import com.andrei.ds.repositories.DoctorRepository;
import com.andrei.ds.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final PatientRepository patientRepository;
    private final CaregiverRepository caregiverRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public LoginService(PatientRepository patientRepository, CaregiverRepository caregiverRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.caregiverRepository = caregiverRepository;
        this.doctorRepository = doctorRepository;
    }

    public LoginDTO attemptLogin(LoginDTO loginDTO) throws ResourceNotFoundException {
        String userType = loginDTO.getUserType();
        String userName = loginDTO.getName();
        String userPassword = loginDTO.getPassword();

        LoginDTO loggedInUserDTO = new LoginDTO(
                loginDTO.getName(),
                loginDTO.getPassword(),
                loginDTO.getUserType()
        );

        switch (userType) {
            case "doctor":
                List<Doctor> doctors = doctorRepository.findAll();
                Optional<Doctor> optionalDoctor = doctors.stream()
                        .filter((doctor -> Objects.equals(doctor.getName(), userName) && Objects.equals(doctor.getPassword(), userPassword)))
                        .findAny();
                if (!optionalDoctor.isPresent()) {
                    LOGGER.error("Doctor with specified credentials does not exist!");
                    throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " not found for login!");
                }
                loggedInUserDTO.setUserId(optionalDoctor.get().getId());
                LOGGER.info("Doctor with id {} logged in!", optionalDoctor.get().getId());
                break;

            case "caregiver":
                List<Caregiver> caregivers = caregiverRepository.findAll();
                Optional<Caregiver> optionalCaregiver = caregivers.stream()
                        .filter((caregiver -> Objects.equals(caregiver.getName(), userName) && Objects.equals(caregiver.getPassword(), userPassword)))
                        .findAny();
                if (!optionalCaregiver.isPresent()) {
                    LOGGER.error("Caregiver with specified credentials does not exist!");
                    throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " not found for login!");
                }
                loggedInUserDTO.setUserId(optionalCaregiver.get().getId());
                LOGGER.info("Caregiver with id {} logged in!", optionalCaregiver.get().getId());
                break;

            case "patient":
                List<Patient> patients = patientRepository.findAll();
                Optional<Patient> optionalPatient = patients.stream()
                        .filter((patient -> Objects.equals(patient.getName(), userName) && Objects.equals(patient.getPassword(), userPassword)))
                        .findAny();
                if (!optionalPatient.isPresent()) {
                    LOGGER.info("Patient with specified credentials does not exist!");
                    throw new ResourceNotFoundException(Patient.class.getSimpleName() + " not found for login!");
                }
                loggedInUserDTO.setUserId(optionalPatient.get().getId());
                LOGGER.info("Patient with id {} logged in!", optionalPatient.get().getId());
                break;

            default:
                throw new ResourceNotFoundException("User type not valid!");

        }
        return loggedInUserDTO;
    }

}
