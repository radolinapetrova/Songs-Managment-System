package com.example.inividual_assignment.bussines;

import com.example.inividual_assignment.domain.Song;

import java.util.Optional;

public interface GetSongUseCase {
    Song getSong(long songId);
}
