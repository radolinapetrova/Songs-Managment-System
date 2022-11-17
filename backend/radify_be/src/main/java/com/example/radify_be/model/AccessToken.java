package com.example.radify_be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccessToken {
    public String subject;
    public Role role;
    Integer userId;
}
