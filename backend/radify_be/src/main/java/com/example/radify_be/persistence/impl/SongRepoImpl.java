package com.example.radify_be.persistence.impl;

import com.example.radify_be.domain.Artist;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.DBRepositories.ArtistDBRepository;
import com.example.radify_be.persistence.DBRepositories.PlaylistDBRepository;
import com.example.radify_be.persistence.DBRepositories.SongDBRepository;
import com.example.radify_be.persistence.SongRepo;
import com.example.radify_be.persistence.entities.ArtistEntity;
import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.SongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SongRepoImpl implements SongRepo {

    private final SongDBRepository repo;

    private final ArtistRepoImpl artistRepo;


    private Song songConverter(SongEntity song){
        List<Integer> artists = song.getArtists().stream().map(ArtistEntity::getId).collect(Collectors.toList());
        return Song.builder()
                .id(song.getId())
                .artists(artists.stream().map(id -> Artist.builder().id(id).build()).collect(Collectors.toList()))
                .title(song.getTitle())
                .genre(song.getGenre())
                .seconds(song.getSeconds())
                .build();
    }

    private SongEntity songEntityConverter(Song song){

        List<ArtistEntity> artists = artistRepo.getArtists(song.getArtists().stream().map(Artist::getId).collect(Collectors.toList()));

        return SongEntity.builder().title(song.getTitle()).genre(song.getGenre()).seconds(song.getSeconds()).artists(artists).build();
    }


    @Override
    public Song save(Song song){
        return songConverter(repo.save(songEntityConverter(song)));
    }

    @Override
    public List<Song> findAllByTitle(String title){
        return repo.findAllByTitleContaining(title).stream().map(song -> songConverter(song)).collect(Collectors.toList());
    }


    @Override
    public List<Song> findAll(){
        List<Song> songs = new ArrayList<>();

        for (SongEntity s : repo.findAll()){
            songs.add(songConverter(s));
        }
        return songs;
    }

    @Override
    public List<Song> findAllByPlaylists(Integer id){
            List<Song> songs = new ArrayList<>();

//        PlaylistEntity playlist = playlistRepo.findById(id).orElse(null);

            for(SongEntity s: repo.findAllByPlaylistsId(id)){
                songs.add(songConverter(s));
            }

            return songs;
    }
}
