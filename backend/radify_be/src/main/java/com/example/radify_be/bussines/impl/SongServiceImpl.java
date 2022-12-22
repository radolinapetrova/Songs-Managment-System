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
        return repository.findAllByTitle(title);
    }

    @Override
    public List<Song> getAllPlaylistSongs(Integer id) {
        return repository.findAllByPlaylists(id);
    }

    @Override
    public Song createSong(Song song){
        return repository.save(song);
    }

    @Override
    public List<Song> getAllSongs(){

        return repository.findAll();
    }


    @Override
    public Song getById(Integer id){
        return repository.getById(id);
    }

}
