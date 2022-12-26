package com.example.radify_be.controller.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePlaylistRequest {
    Integer playlistId;
    Integer userId;
}
