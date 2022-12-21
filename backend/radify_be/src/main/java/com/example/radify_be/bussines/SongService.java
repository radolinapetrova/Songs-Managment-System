package com.example.radify_be.bussines;

import com.example.radify_be.domain.Song;

import java.util.List;

public interface SongService {

    Song createSong(Song song);
    List<Song> getSongsByTitle(String title);

    public List<Song> getAllSongs();

    List<Song> getAllPlaylistSongs(Integer id);

}
