package com.example.radify_be.bussines.exceptions;

public class UnauthorizedAction extends RuntimeException{
    public UnauthorizedAction(){
        super("Out of your authority sunshine");
    }
}
