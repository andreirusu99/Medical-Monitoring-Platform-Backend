package com.andrei.ds.controllers;

import com.andrei.ds.DTOs.PatientDTO;
import com.andrei.ds.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public ResponseEntity<List<PatientDTO>> getPatients(
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        List<PatientDTO> patients = patientService.retrievePatients(userId, userType);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<PatientDTO> getPatientById(
            @PathVariable("id") UUID patientId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        PatientDTO patientDTO = patientService.retrievePatient(patientId, userId, userType);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertPatient(
            @Valid @RequestBody PatientDTO patientDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID patientId = patientService.insertPatient(patientDTO, userId, userType);
        return new ResponseEntity<>(patientId, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PatientDTO> updatePatientDetails(
            @Valid @RequestBody PatientDTO patientDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        PatientDTO newDetails = patientService.updatePatientDetails(patientDTO, userId, userType);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UUID> deletePatient(
            @PathVariable("id") UUID patientId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID deletedId = patientService.deletePatient(patientId, userId, userType);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }
}
