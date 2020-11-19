package com.andrei.ds.controllers;

import com.andrei.ds.DTOs.TreatmentDTO;
import com.andrei.ds.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping(value = "/of/{id}")
    public ResponseEntity<List<TreatmentDTO>> getPatientTreatments(
            @PathVariable("id") UUID patientId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        List<TreatmentDTO> treatments = treatmentService.retrievePatientTreatments(patientId, userId, userType);
        return new ResponseEntity<>(treatments, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<TreatmentDTO> getTreatmentById(
            @PathVariable("id") UUID treatmentId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        TreatmentDTO treatmentDTO = treatmentService.retrieveTreatment(treatmentId, userId, userType);
        return new ResponseEntity<>(treatmentDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> addTreatment(
            @Valid @RequestBody TreatmentDTO treatmentDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID treatmentId = treatmentService.addTreatment(treatmentDTO, userId, userType);
        return new ResponseEntity<>(treatmentId, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TreatmentDTO> updateTreatmentDetails(
            @Valid @RequestBody TreatmentDTO treatmentDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        TreatmentDTO newDetails = treatmentService.updateTreatmentDetails(treatmentDTO, userId, userType);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UUID> deleteTreatment(
            @PathVariable("id") UUID treatmentId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID deletedId = treatmentService.deleteTreatment(treatmentId, userId, userType);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

}
