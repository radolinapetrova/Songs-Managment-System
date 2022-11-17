package com.example.radify_be.model.requests;


import com.example.radify_be.model.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongRequest {
    private String title;
    private Integer seconds;
    private String genre;
    private List<Artist> artists;
}
