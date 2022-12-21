package com.example.radify_be.bussines.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedAction extends RuntimeException{
    public UnauthorizedAction(){
        super("Out of your authority sunshine");
    }
}
