package com.example.radify_be.bussines;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.domain.User;

public interface UserService {

    User register (User user) throws UnsuccessfulAction;
    void validateEmail(String email) throws InvalidInputException;

    User getById(Integer id);
    void deleteUser(Integer id) throws UnsuccessfulAction;

    User updateUser(User user) throws UnsuccessfulAction, InvalidInputException;

}
