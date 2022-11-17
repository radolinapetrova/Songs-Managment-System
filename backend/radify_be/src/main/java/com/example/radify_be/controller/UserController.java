package com.example.radify_be.controller;

import com.example.radify_be.bussines.UserService;
import com.example.radify_be.model.Role;
import com.example.radify_be.model.requests.CreateUserRequest;
import com.example.radify_be.model.requests.LoginRequest;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {


    private final UserService userService;


    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest userRequest) {
        userService.register(UserEntity.builder().first_name(userRequest.getFirst_name()).last_name(userRequest.getLast_name()).role(Role.USER).username(userRequest.getUsername()).email(userRequest.getEmail()).password(userRequest.getPassword()).build());
        return ResponseEntity.ok().body("Account created");
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }


}
