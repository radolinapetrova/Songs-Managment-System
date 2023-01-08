package com.example.radify_be.bussines.exceptions;

public class DublicateDataException extends RuntimeException{
    public DublicateDataException(){
        super("The data you have entered is already used!");
    }
}
