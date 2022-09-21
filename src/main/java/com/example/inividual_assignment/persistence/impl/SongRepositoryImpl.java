package com.example.inividual_assignment.persistence.impl;


import com.example.inividual_assignment.domain.Song;
import com.example.inividual_assignment.persistence.SongRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SongRepositoryImpl implements SongRepository {

    private final List<Song> songList;

    public SongRepositoryImpl() {
        this.songList = new ArrayList<>();
    }

    @Override
    public boolean existsById(long songId) {
        return this.songList
                .stream()
                .anyMatch(Song -> Song.getId() == (songId));
    }

    @Override
    public Song getById(long songId) {
        return this.songList
                .stream()
                .filter(Song -> Song.getId() == songId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public int count() {
        return this.songList.size();
    }

    @Override
    public List<Song> getAllByGenre(String genre) {
        return this.songList
                .stream()
                .filter(Song -> Song.getGenre() == genre)
                .toList();
    }

    @Override
    public Song save(Song song) {
        this.songList.add(song);
        return song;
    }
}
