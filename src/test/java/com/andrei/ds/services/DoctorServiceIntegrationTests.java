package com.andrei.ds.services;

import com.andrei.ds.DTOs.DoctorDTO;
import com.andrei.ds.Ds2020TestConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/sql/create.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/sql/delete.sql")
public class DoctorServiceIntegrationTests extends Ds2020TestConfig {

    @Autowired
    DoctorService doctorService;

    @Test
    public void testGetCorrect() {
        List<DoctorDTO> doctorDTOList = doctorService.retrieveDoctors();
        assertEquals("Test Insert Doctor", 1, doctorDTOList.size());
    }

    @Test
    public void testInsertCorrectWithGetAll() {
        DoctorDTO doctorDTO = new DoctorDTO("Ray", "secret2", "Some other place", 21, "neurology");
        doctorService.insertDoctor(doctorDTO);

        List<DoctorDTO> doctorDTOList = doctorService.retrieveDoctors();
        assertEquals("Test Inserted Doctors", 2, doctorDTOList.size());
    }

}
