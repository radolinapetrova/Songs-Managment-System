package com.example.radify_be.bussines;

import com.example.radify_be.model.Song;
import com.example.radify_be.persistence.entities.SongEntity;

import java.util.List;

public interface SongService {

    public SongEntity createSong(SongEntity song);

    public List<Song> getSongsByTitle(String title);
}
