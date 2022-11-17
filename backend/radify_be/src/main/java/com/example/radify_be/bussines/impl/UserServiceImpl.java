package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.AccessTokenDecoder;
import com.example.radify_be.bussines.AccessTokenEncoder;
import com.example.radify_be.bussines.UserService;
import com.example.radify_be.bussines.exceptions.InvalidCredentialsException;
import com.example.radify_be.bussines.impl.converters.PlaylistConverter;
import com.example.radify_be.bussines.impl.converters.UserConverter;
import com.example.radify_be.model.AccessToken;
import com.example.radify_be.model.Playlist;
import com.example.radify_be.model.User;
import com.example.radify_be.model.requests.LoginRequest;
import com.example.radify_be.persistence.UserRepository;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder encoder;
    private final AccessTokenDecoder decoder;


    @Override
    public void register(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Override
    public UserEntity getById(Integer id){
           return repository.findById(id).orElse(null);
    }

    @Override
    public void validateEmail(String email) throws Exception {

        Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$");
        Matcher match = pattern.matcher(email);

        if (!match.matches()){
            throw new Exception("Wrong email");
        }
    }

    @Override
    public String login(LoginRequest request){

        User user =  UserConverter.convert(repository.findByUsername(request.getUsername()));
        if (user.equals(null)){
            throw new InvalidCredentialsException();
        }

        if (!passwordMatch(request.getPassword(), user.getAccount().getPassword())){
            throw new InvalidCredentialsException();
        }

        return generateAccessToken(user);
    }


    private boolean passwordMatch(String rawPass, String encodedPass) {
        return passwordEncoder.matches(rawPass, encodedPass);
    }


    private String generateAccessToken(User user){
        return encoder.encode(AccessToken.builder().subject(user.getAccount().getUsername()).userId(user.getId()).role(user.getRole()).build());
    }



}
