package com.example.radify_be.controller.requests;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlaylistRequest {
    String title;
    boolean isPublic;
    Integer userId;
}
