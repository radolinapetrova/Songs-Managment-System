package com.example.radify_be.persistence;

import com.example.radify_be.domain.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepo {
    public Song save(Song song);

    public List<Song> findAllByTitle(String title, Pageable pageable);

}
