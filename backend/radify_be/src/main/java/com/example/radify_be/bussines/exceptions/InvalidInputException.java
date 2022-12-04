package com.example.radify_be.bussines.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidInputException extends ResponseStatusException {
    public InvalidInputException() {
        super(HttpStatus.NOT_ACCEPTABLE, "You have entered invalid data, which is quite unacceptable");
    }


}
