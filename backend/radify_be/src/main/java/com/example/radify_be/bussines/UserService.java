package com.example.radify_be.bussines;

import com.example.radify_be.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User register (User user) throws Exception;
    void validateEmail(String email) throws Exception;

    User getById(Integer id);
    void deleteUser(Integer id) throws Exception;



}
