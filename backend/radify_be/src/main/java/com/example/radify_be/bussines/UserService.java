package com.example.radify_be.bussines;

import com.example.radify_be.model.Playlist;
import com.example.radify_be.model.requests.LoginRequest;
import com.example.radify_be.persistence.entities.UserEntity;

import java.util.List;

public interface UserService {

    void register(UserEntity user);
    void validateEmail(String email) throws Exception;
    String login(LoginRequest request);

    UserEntity getById(Integer id);


}
