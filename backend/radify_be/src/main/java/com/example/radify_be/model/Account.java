package com.example.radify_be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Account {
    private String username;
    private String email;
    private String password;
}
