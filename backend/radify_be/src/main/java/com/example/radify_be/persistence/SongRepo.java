package com.example.radify_be.persistence;

import com.example.radify_be.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepo {
    Song save(Song song);

    List<Song> findAllByTitle(String title);

    List<Song> findAll();

    List<Song> findAllByPlaylists(Integer id);

    void deleteById(Integer id);

    Song getById(Integer id);

    List<Song> getAllByIdIn(List<Integer> ids);

    boolean existsById(Integer id);
}
