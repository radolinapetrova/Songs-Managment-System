package com.example.inividual_assignment.bussines.impl;

import com.example.inividual_assignment.bussines.GetSongUseCase;
import com.example.inividual_assignment.domain.Song;
import com.example.inividual_assignment.persistence.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetSongUseCaseImpl implements GetSongUseCase {

    private SongRepository songRepository;

    @Override
    public Song getSong(long songId) {
        return songRepository.getById(songId);
    }
}
