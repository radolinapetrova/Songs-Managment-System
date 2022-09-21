package com.example.inividual_assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Song {
    private long id;
    private String title;
    private int listeners;
    private String genre;
}
