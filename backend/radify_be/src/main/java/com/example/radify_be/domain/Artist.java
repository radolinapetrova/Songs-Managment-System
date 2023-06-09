package com.example.radify_be.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private int id;
    private String fName;
    private String lName;
    private List<Song> songs;
}

