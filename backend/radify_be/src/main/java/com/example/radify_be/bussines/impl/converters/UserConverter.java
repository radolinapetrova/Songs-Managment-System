package com.example.radify_be.bussines.impl.converters;


import com.example.radify_be.model.Account;
import com.example.radify_be.model.User;
import com.example.radify_be.persistence.entities.UserEntity;

public class UserConverter {

    public static User convert(UserEntity user){
        Account account = Account.builder().username(user.getUsername()).password(user.getPassword()).email(user.getEmail()).build();
        return User.builder().id(user.getId()).fName(user.getFName()).lName(user.getLName()).role(user.getRole()).account(account).build();
    }
}
