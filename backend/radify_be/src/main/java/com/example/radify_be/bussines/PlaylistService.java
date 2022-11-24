package com.example.radify_be.bussines;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;

import java.util.List;

public interface PlaylistService {
    Playlist createPlaylist(Playlist playlist);

    Playlist getPlaylistSongs(Integer playlistId);

    public List<Playlist> getUserPlaylists(Integer id);
    public void deletePlaylist(Integer id) throws Exception;
    public void addSongToPlaylist(Playlist playlist, Song song);

    public Playlist findById(Integer id);
}
