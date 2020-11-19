package com.andrei.ds.controllers;

import com.andrei.ds.DTOs.LoginDTO;
import com.andrei.ds.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public ResponseEntity<LoginDTO> attemptUserLogin(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginDTO newLoginDTO = loginService.attemptLogin(loginDTO);

        if (newLoginDTO.getUserId() != null) { // login successful
            response.addHeader("Set-Cookie", "userId=" + newLoginDTO.getUserId() + "; Expires=Session; Path=/; HttpOnly;Secure;SameSite=None");
            response.addHeader("Set-Cookie", "userType=" + newLoginDTO.getUserType() + "; Expires=Session; Path=/; HttpOnly;Secure;SameSite=None");
        }
        return new ResponseEntity<>(newLoginDTO, HttpStatus.OK);
    }

}
