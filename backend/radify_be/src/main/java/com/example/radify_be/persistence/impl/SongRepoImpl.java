package com.example.radify_be.persistence.impl;

import com.example.radify_be.domain.Artist;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.DBRepositories.SongDBRepository;
import com.example.radify_be.persistence.SongRepo;
import com.example.radify_be.persistence.entities.ArtistEntity;
import com.example.radify_be.persistence.entities.SongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SongRepoImpl implements SongRepo {

    private final SongDBRepository repo;

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
        return SongEntity.builder().id(song.getId()).build();
    }


    @Override
    public Song save(Song song){
        return songConverter(repo.save(songEntityConverter(song)));
    }

    @Override
    public List<Song> findAllByTitle(String title){
        return repo.findAllByTitle(title).stream().map(song -> songConverter(song)).collect(Collectors.toList());
    }


    @Override
    public List<Song> findAll(){
        List<Song> songs = new ArrayList<>();

        for (SongEntity s : repo.findAll()){
            songs.add(songConverter(s));
        }
        return songs;
    }

//    @Override
//    public List<Song> findAllByPlaylistId(Integer id){
//            List<Song> songs = new ArrayList<>();
//
//            for(SongEntity s: repo.findAllByPlaylistId(id)){
//                songs.add(songConverter(s));
//            }
//
//            return songs;
//    }
}
