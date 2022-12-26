package com.example.radify_be.persistence;

import com.example.radify_be.domain.Playlist;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepo {
    Playlist save(Playlist playlist);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<Playlist> getAllByUserId(Integer id);

    Playlist findById(Integer id);

    List<Playlist> getAllPublicAndUser(Integer id);

    List<Playlist> findByTitle(String title, Integer id);

    Playlist update(Integer playlistId, Integer songId);

    Playlist deleteSong(Integer playlistId, Integer songId);



}
