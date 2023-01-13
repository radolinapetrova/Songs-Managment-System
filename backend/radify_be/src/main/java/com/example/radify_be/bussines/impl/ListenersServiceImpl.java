package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.ListenersService;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.ListenersRepo;
import com.example.radify_be.persistence.SongRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListenersServiceImpl implements ListenersService {

    private final ListenersRepo repo;
    private final SongRepo songRepo;

    @Override
    public long getMonthlyListeners(Integer id){
        return repo.getMonthlyListeners(id);
    }

    @Override
    public long getAvgListeners(Integer id){
        return repo.getAvgListeners(id);
    }

    @Override
    public void save(Integer song, Integer user){
        repo.save(song, user);
    }

    @Override
    public List<Song> getTopSongs(){

        List <Integer> ids = repo.getTopSongs().stream().map(id -> (int)(long)id).collect(Collectors.toList());

        return songRepo.getAllByIdIn(ids);
    }
}


