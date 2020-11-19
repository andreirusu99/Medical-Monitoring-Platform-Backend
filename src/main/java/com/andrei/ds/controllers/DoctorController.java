package com.andrei.ds.controllers;


import com.andrei.ds.DTOs.DoctorDTO;
import com.andrei.ds.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping()
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<DoctorDTO> doctors = doctorService.retrieveDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(
            @PathVariable("id") UUID doctorId,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        DoctorDTO dto = doctorService.retrieveDoctorById(doctorId, userId, userType);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        UUID doctorId = doctorService.insertDoctor(doctorDTO);
        return new ResponseEntity<>(doctorId, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<DoctorDTO> updateDoctorDetails(
            @Valid @RequestBody DoctorDTO doctorDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        DoctorDTO newDetails = doctorService.updateDoctorDetails(doctorDTO, userId, userType);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }
}
