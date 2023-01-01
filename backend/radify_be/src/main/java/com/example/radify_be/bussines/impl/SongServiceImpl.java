package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.domain.Role;
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
    public List<Song> getSongsByTitle(String title){
        return repository.findAllByTitle(title);
    }


    @Override
    public void deleteSong (Integer id, Integer user) throws UnauthorizedAction {

        log.info("Role is {}", userRepo.findById(user).getRole());
        if (userRepo.findById(user).getRole() == Role.USER){
            throw new UnauthorizedAction();
        }
        repository.deleteById(id);
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

    @Override
    public List<Song> getAllByIdIn(List<Integer> ids){
     return repository.getAllByIdIn(ids);
    }

}
