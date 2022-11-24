package com.example.radify_be.bussines;

import com.example.radify_be.domain.User;

public interface UserService {

    void register (User user) throws Exception;
    void validateEmail(String email) throws Exception;

    User getById(Integer id);
    public void deleteUser(Integer id) throws Exception;


}
