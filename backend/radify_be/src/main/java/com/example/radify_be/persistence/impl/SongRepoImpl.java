package com.example.radify_be.persistence.impl;

import com.example.radify_be.domain.Artist;
import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.DBRepositories.SongDBRepository;
import com.example.radify_be.persistence.SongRepo;
import com.example.radify_be.persistence.entities.ArtistEntity;
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


    public static Song songConverter(SongEntity song){

        if(song == null){
            return null;
        }

        return Song.builder()
                .id(song.getId())
                .artists(song.getArtists().stream().map(artist -> Artist.builder().id(artist.getId()).fName(artist.getFName()).lName(artist.getLName()).build()).collect(Collectors.toList()))
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
    public  Song getById(Integer id) {
        return songConverter(repo.findById(id).orElse(null));
    }


    @Override
    public Song save(Song song){
        return songConverter(repo.save(songEntityConverter(song)));
    }

    @Override
    public void deleteById(Integer id){
        repo.deleteById(id);
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
    public boolean existsById(Integer id){
        return repo.existsById(id);
    }

    @Override
    public List<Song> findAllByPlaylists(Integer id){
            List<Song> songs = new ArrayList<>();
            List<SongEntity> result = repo.findAllByPlaylistsId(id);

            if (result.size() == 0){
                return new ArrayList<Song>();
            }

            for(SongEntity s: result){
                songs.add(songConverter(s));
            }

            return songs;
    }


    @Override
    public List<Song> getAllByIdIn(List<Integer> ids){
        return repo.getAllByIdIn(ids).stream().map(s -> songConverter(s)).collect(Collectors.toList());
    }

}
