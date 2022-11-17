package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.impl.converters.PlaylistConverter;
import com.example.radify_be.model.Playlist;
import com.example.radify_be.persistence.PlaylistRepository;
import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {


    private PlaylistRepository repository;

    @Override
    public Playlist getPlaylistSongs(Integer playlistId) {
        Playlist playlist = PlaylistConverter.convert(repository.getReferenceById(playlistId));
        //custom method for getting all the songs with certain playlist id
//        List<String> songs = songsRepository.findAllByPlaylistId(playlistId).stream().map(PlaylistSongsEntity::getSongId).collect(Collectors.toList());
//        playlist.setSongs(songs);
        return playlist;
    }

    @Override
    public PlaylistEntity createPlaylist(PlaylistEntity playlist) {
        return repository.save(playlist);
    }


    @Override
    public List<Playlist> getUserPlaylists(Integer id){
        return repository.getReferencesByUsersId(id).stream().map(PlaylistConverter::convert).collect(Collectors.toList());
    }
}
