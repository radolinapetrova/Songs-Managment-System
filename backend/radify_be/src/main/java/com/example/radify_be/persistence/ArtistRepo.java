package com.example.radify_be.persistence;

import com.example.radify_be.domain.Artist;
import com.example.radify_be.persistence.entities.ArtistEntity;

import java.util.List;

public interface ArtistRepo {

    Artist save(Artist srtist);

    void deleteById(Integer id);

    Artist findById(Integer id);

    List<ArtistEntity> getArtists(List<Integer> ids);
}

