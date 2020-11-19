package com.andrei.ds.controllers;

import com.andrei.ds.DTOs.DoctorDTO;
import com.andrei.ds.Ds2020TestConfig;
import com.andrei.ds.services.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorControllerUnitTest extends Ds2020TestConfig {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService service;

    @Test
    public void insertDoctorTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DoctorDTO doctorDTO = new DoctorDTO("John", "secret", "Some place", 22, "cardiology");

        mockMvc.perform(post("/doctor")
                .content(objectMapper.writeValueAsString(doctorDTO))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    public void insertDoctorTestFailsDueToAge() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DoctorDTO doctorDTO = new DoctorDTO("John", "secret", "Some place", 17, "cardiology");

        mockMvc.perform(post("/doctor")
                .content(objectMapper.writeValueAsString(doctorDTO))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertDoctorTestFailsDueToNull() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DoctorDTO doctorDTO = new DoctorDTO("John", "secret", null, 22, "cardiology");

        mockMvc.perform(post("/doctor")
                .content(objectMapper.writeValueAsString(doctorDTO))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}
