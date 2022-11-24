package com.example.radify_be.bussines;

import com.example.radify_be.domain.Song;

import java.util.List;

public interface SongService {

    public Song createSong(Song song);
    public List<Song> getSongsByTitle(String title);

}
