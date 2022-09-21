package com.example.inividual_assignment.persistence;


import com.example.inividual_assignment.domain.Song;

import java.util.List;

public interface SongRepository {

    boolean existsById(long songId);

    Song getById(long songId);

    int count();

    public Song save(Song song);

    List<Song> getAllByGenre(String genre);
}
