package com.example.radify_be.controller.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    Integer id;
    String username;
    String email;
    String first_name;
    String last_name;
}
