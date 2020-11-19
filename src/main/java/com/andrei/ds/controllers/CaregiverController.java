package com.andrei.ds.controllers;

import com.andrei.ds.DTOs.CaregiverDTO;
import com.andrei.ds.services.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;

    @Autowired
    public CaregiverController(CaregiverService caregiverService) {
        this.caregiverService = caregiverService;
    }

    @GetMapping()
    public ResponseEntity<List<CaregiverDTO>> getCaregivers(
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        List<CaregiverDTO> caregivers = caregiverService.retrieveAllCaregivers(userId, userType);
        return new ResponseEntity<>(caregivers, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<CaregiverDTO> getCaregiver(
            @PathVariable("id") UUID caregiverId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        CaregiverDTO caregiver = caregiverService.retrieveCaregiver(caregiverId, userId, userType);
        return new ResponseEntity<>(caregiver, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> addCaregiver(
            @Valid @RequestBody CaregiverDTO caregiverDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID caregiverId = caregiverService.insertCaregiver(caregiverDTO, userId, userType);
        return new ResponseEntity<>(caregiverId, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CaregiverDTO> updateCaregiver(
            @Valid @RequestBody CaregiverDTO caregiverDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        CaregiverDTO newDetails = caregiverService.updateCaregiver(caregiverDTO, userId, userType);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UUID> deleteCaregiver(
            @PathVariable("id") UUID caregiverId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID deletedId = caregiverService.deleteCaregiver(caregiverId, userId, userType);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }


}
