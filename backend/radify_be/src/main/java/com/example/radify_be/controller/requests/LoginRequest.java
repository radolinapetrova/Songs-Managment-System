package com.example.radify_be.controller.requests;

import com.example.radify_be.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
    private Role role;
}
