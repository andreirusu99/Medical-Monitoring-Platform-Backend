package com.andrei.ds.controllers;

import com.andrei.ds.DTOs.DrugDTO;
import com.andrei.ds.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/drug")
public class DrugController {

    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping()
    public ResponseEntity<List<DrugDTO>> getDrugs(
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        List<DrugDTO> drugs = drugService.retrieveDrugs(userId, userType);
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{name}")
    public ResponseEntity<DrugDTO> getDrug(
            @PathVariable("name") String drugName,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        DrugDTO drugDTO = drugService.retrieveDrug(drugName, userId, userType);
        return new ResponseEntity<>(drugDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDrug(
            @Valid @RequestBody DrugDTO drugDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID drugID = drugService.insertDrug(drugDTO, userId, userType);
        return new ResponseEntity<>(drugID, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<DrugDTO> updateDrugDetails(
            @Valid @RequestBody DrugDTO drugDTO,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        DrugDTO newDetails = drugService.updateDrugDetails(drugDTO, userId, userType);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{name}")
    public ResponseEntity<UUID> deleteDrug(
            @PathVariable("name") String drugName,
            @CookieValue(value = "userType") String userType,
            @CookieValue(value = "userId") UUID userId
    ) {
        UUID deletedId = drugService.deleteDrug(drugName, userId, userType);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

}
