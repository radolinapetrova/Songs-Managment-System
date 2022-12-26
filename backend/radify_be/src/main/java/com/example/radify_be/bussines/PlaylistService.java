package com.example.radify_be.bussines;

import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.controller.requests.DeletePlaylistRequest;
import com.example.radify_be.domain.Playlist;

import java.util.List;

public interface PlaylistService {
    Playlist createPlaylist(Playlist playlist);

    Playlist getPlaylistSongs(Integer playlistId);

    List<Playlist> getUserPlaylists(Integer id);
    void deletePlaylist(Integer playlist, Integer user) throws UnauthorizedAction, UnsuccessfulAction;
    Playlist addSongToPlaylist(Integer playlist, Integer song, Integer user) throws UnauthorizedAction, UnsuccessfulAction;

    Playlist findById(Integer id);

    List<Playlist> getAllByTitle(Integer id, String title);

    List<Playlist> getAllPublicAndUser(Integer id);

    Playlist removeSongsFromPlaylist(Integer playlist, Integer song, Integer user) throws UnauthorizedAction, UnsuccessfulAction;
}
