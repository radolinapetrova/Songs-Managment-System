package com.example.radify_be.bussines;

import com.example.radify_be.model.Playlist;
import com.example.radify_be.persistence.entities.PlaylistEntity;

import java.util.List;

public interface PlaylistService {
    PlaylistEntity createPlaylist(PlaylistEntity playlist);

    Playlist getPlaylistSongs(Integer playlistId);

    public List<Playlist> getUserPlaylists(Integer id);
}
