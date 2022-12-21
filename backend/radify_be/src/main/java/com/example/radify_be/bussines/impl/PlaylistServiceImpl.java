package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.persistence.PlaylistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {


    private final PlaylistRepo repo;

    @Override
    public Playlist getPlaylistSongs(Integer playlistId) {
        return null;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return repo.save(playlist);
    }


    @Override
    public List<Playlist> getUserPlaylists(Integer id) {
        return repo.getAllByUserId(id);
    }

    @Override
    public void deletePlaylist(Integer id) throws RuntimeException {
        //if a user that didn't create the playlist tries to delete it
       // if (!pl.getCreator().getId().equals(id)) {
            //throw new UnauthorizedAction();
       // }
        repo.deleteById(id);
    }

    @Override
    public void addSongToPlaylist(Integer playlist, Integer song) throws RuntimeException {
        repo.update(playlist, song);
    }

    @Override
    public void removeSongsFromPlaylist(Integer playlist, Integer song) {
        repo.delete(playlist, song);
    }

    @Override
    public Playlist findById(Integer id) {
        return repo.findById(id);
    }


    @Override
    public List<Playlist> getAllPublicAndUser(Integer id) {
        return repo.getAllPublicAndUser(id);
    }

    @Override
    public List<Playlist> getAllByTitle(Integer id, String title) {
        return repo.findByTitle(title, id);
    }
}
