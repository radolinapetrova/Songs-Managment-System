package com.example.radify_be.persistence;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepo {
    public Playlist save(Playlist playlist);

    public void deleteById(Integer id);

    public boolean existsById(Integer id);

    public List<Playlist> getAllByUserId(Integer id);

    public Playlist findById(Integer id);

}
