package com.example.radify_be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    private Integer id;
    private String title;
    private Integer seconds;
    public String genre;
    List<Artist> artists;
}
