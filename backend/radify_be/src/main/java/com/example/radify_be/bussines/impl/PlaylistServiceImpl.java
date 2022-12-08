package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.PlaylistRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void deletePlaylist(Integer id) throws Exception {
        repo.deleteById(id);
        if (repo.existsById(id)){
            throw new Exception("Unsuccessful deletion of playlist");
        }
    }

    @Override
    public void addSongToPlaylist(Integer playlist, Integer song){
        //playlist.getSongs().add(song);
        repo.update(playlist, song);
    }

    @Override
    public void removeSongsFromPlaylist(Integer playlist, Integer song){
        repo.delete(playlist, song);
    }

    @Override
    public Playlist findById(Integer id){
        return repo.findById(id);
    }


    @Override
    public List<Playlist> getAllPublicAndUser(Integer id){
        return repo.getAllPublicAndUser(id);
    }

    @Override
    public List<Playlist> getAllByTitle(Integer id, String title){
        return repo.findByTitle(title, id);
    }
}
