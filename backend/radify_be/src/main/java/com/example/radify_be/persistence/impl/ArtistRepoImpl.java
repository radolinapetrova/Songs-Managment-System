package com.example.radify_be.persistence.impl;

import com.example.radify_be.domain.Artist;
import com.example.radify_be.persistence.ArtistRepo;
import com.example.radify_be.persistence.DBRepositories.ArtistDBRepository;
import com.example.radify_be.persistence.entities.ArtistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArtistRepoImpl implements ArtistRepo {
    private final ArtistDBRepository repo;


    @Override
    public Artist save(Artist artist) {
        return artistConverter(repo.save(artistEntityConverter(artist)));
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Artist findById(Integer id) {
        return artistConverter(repo.findById(id).orElse(null)); //NOSONAR
    }

    @Override
    public List<ArtistEntity> getArtists(List<Integer> ids){
        return repo.findByIdIn(ids);
    }



    private Artist artistConverter(ArtistEntity artist){

//        List<Song> songs = new ArrayList<>();
//        for(SongEntity s : artist.getSongs()){
//            songs.add(Song.builder().id(s.getId()).build());
//        }

        return Artist.builder().id(artist.getId()).fName(artist.getFName()).lName(artist.getLName())
                //.songs(songs)
                .build();
    }

    private ArtistEntity artistEntityConverter(Artist artist){
        return ArtistEntity.builder().id(artist.getId()).fName(artist.getFName()).lName(artist.getLName()).build();
    }
}
