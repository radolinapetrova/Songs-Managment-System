package com.example.radify_be.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSongRequest {
    Integer playlistId;
    String songId;
}
