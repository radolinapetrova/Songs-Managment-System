package com.example.radify_be.bussines;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.domain.Song;

import java.util.List;

public interface SongService {

    Song createSong(Song song);
    List<Song> getSongsByTitle(String title);

    public List<Song> getAllSongs();

    List<Song> getAllPlaylistSongs(Integer id);

    Song getById(Integer id) throws InvalidInputException;

    void deleteSong (Integer id, Integer user)throws UnauthorizedAction, InvalidInputException;

    List<Song> getAllByIdIn(List<Integer> ids);

}
