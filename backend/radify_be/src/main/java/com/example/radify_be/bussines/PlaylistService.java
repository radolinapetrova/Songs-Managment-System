package com.example.radify_be.bussines;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;

import java.util.List;

public interface PlaylistService {
    Playlist createPlaylist(Playlist playlist);

    Playlist getPlaylistSongs(Integer playlistId);

    List<Playlist> getUserPlaylists(Integer id);
    void deletePlaylist(Integer id) throws Exception;
    void addSongToPlaylist(Playlist playlist, Song song);

    Playlist findById(Integer id);

    List<Playlist> getAllPublicAndUser(Integer id);
}
