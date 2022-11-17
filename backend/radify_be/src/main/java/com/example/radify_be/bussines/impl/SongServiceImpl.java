package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.bussines.impl.converters.SongConverter;
import com.example.radify_be.model.Song;
import com.example.radify_be.persistence.SongRepository;
import com.example.radify_be.persistence.entities.SongEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    SongRepository repository;

    @Override
    public List<Song> getSongsByTitle(String title){
        Pageable pageable = PageRequest.of(0, 5);
        return repository.findAllByTitle(title, pageable).stream().map(SongConverter::convert).collect(Collectors.toList());
        //return null;
    }

    @Override
    public SongEntity createSong(SongEntity song){
        return repository.save(song);
    }
}
