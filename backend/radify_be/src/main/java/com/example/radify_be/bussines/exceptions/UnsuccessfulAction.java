package com.example.radify_be.bussines.exceptions;

public class UnsuccessfulAction extends RuntimeException{
    public UnsuccessfulAction(){
        super("Sadly, it seems that, unfortunately, you have regrettably done absolutely nothing");
    }
}
