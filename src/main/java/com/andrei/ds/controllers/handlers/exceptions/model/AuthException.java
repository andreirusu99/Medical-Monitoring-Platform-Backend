package com.andrei.ds.controllers.handlers.exceptions.model;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class AuthException extends CustomException {
    private static final String MESSAGE = "Authorization error!";
    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public AuthException(String resource) {
        super(MESSAGE, httpStatus, resource, new ArrayList<>());
    }

}
