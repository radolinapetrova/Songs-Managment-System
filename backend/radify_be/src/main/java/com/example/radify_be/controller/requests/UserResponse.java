package com.example.radify_be.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    public Integer id;
    public String fName;
    public String lName;
    public String username;
    public String email;
}
