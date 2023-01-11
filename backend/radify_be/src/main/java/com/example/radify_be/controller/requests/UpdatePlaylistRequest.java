package com.example.radify_be.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlaylistRequest {

    Integer playlist;
    boolean isPublic;
    Integer user;
}
