package com.example.radify_be.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditPlaylistSongsRequest {
    Integer playlistId;
    Integer songId;
    Integer userId;
}
