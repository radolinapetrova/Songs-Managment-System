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
        //Playlist playlist = PlaylistConverter.convert(repo.getReferenceById(playlistId));
        //custom method for getting all the songs with certain playlist id
//        List<String> songs = songsRepository.findAllByPlaylistId(playlistId).stream().map(PlaylistSongsEntity::getSongId).collect(Collectors.toList());
//        playlist.setSongs(songs);
        //return playlist;
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
    public void addSongToPlaylist(Playlist playlist, Song song){
        playlist.getSongs().add(song);
        repo.save(playlist);
    }

    @Override
    public Playlist findById(Integer id){
        return repo.findById(id);
    }
}
