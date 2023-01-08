package com.example.radify_be.bussines.exceptions;

public class UnsuccessfulOperation extends RuntimeException{
    public UnsuccessfulOperation(){
        super("The operation you tried to do was unsuccessfulS");
    }
}
