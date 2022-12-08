package com.example.radify_be.bussines;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;

import java.util.List;

public interface PlaylistService {
    Playlist createPlaylist(Playlist playlist);

    Playlist getPlaylistSongs(Integer playlistId);

    List<Playlist> getUserPlaylists(Integer id);
    void deletePlaylist(Integer id) throws Exception;
    void addSongToPlaylist(Integer playlist, Integer song);

    Playlist findById(Integer id);

    List<Playlist> getAllByTitle(Integer id, String title);

    List<Playlist> getAllPublicAndUser(Integer id);

    void removeSongsFromPlaylist(Integer playlist, Integer song);
}
