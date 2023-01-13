package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.SongRepo;
import com.example.radify_be.persistence.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongRepo repository;
    private final UserRepo userRepo;

    @Override
    public List<Song> getSongsByTitle(String title) {
        if (title.equals("") || title == null){
            return repository.findAll();
        }
        return repository.findAllByTitle(title);
    }


    @Override
    public void deleteSong(Integer id, Integer user) throws UnauthorizedAction, InvalidInputException {

        if (!userRepo.findById(user).getRole().toString().equals("ADMIN")) {
            throw new UnauthorizedAction();
        }

        if (!repository.existsById(id)){
            throw new InvalidInputException();
        }

        repository.deleteById(id);
    }

    @Override
    public List<Song> getAllPlaylistSongs(Integer id) {
        return repository.findAllByPlaylists(id);
    }

    @Override
    public Song createSong(Song song) {
        return repository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return repository.findAll();
    }


    @Override
    public Song getById(Integer id) throws InvalidInputException {

        Song song = repository.getById(id);
        if (song == null){
            throw new InvalidInputException();
        }
        return song;
    }

    @Override
    public List<Song> getAllByIdIn(List<Integer> ids) {
        return repository.getAllByIdIn(ids);
    }

}
