package com.example.radify_be.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    String username;
    String email;
    String password;
    String first_name;
    String last_name;
}
