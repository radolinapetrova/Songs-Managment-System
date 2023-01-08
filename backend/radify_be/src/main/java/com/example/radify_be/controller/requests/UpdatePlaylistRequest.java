package com.example.radify_be.controller.requests;

import com.example.radify_be.domain.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdatePlaylistRequest {

    Integer playlist;
    Boolean isPublic;
    Integer user;
}
