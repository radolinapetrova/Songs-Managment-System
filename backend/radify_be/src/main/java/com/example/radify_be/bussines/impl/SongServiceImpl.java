package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.SongRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepo repository;

    @Override
    public List<Song> getSongsByTitle(String title){
        Pageable pageable = PageRequest.of(0, 5);
        return repository.findAllByTitle(title, pageable);
    }

    @Override
    public Song createSong(Song song){
        return repository.save(song);
    }
}
