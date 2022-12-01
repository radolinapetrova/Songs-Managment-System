package com.example.radify_be.persistence;

import com.example.radify_be.domain.Artist;

public interface ArtistRepo {

    Artist save(Artist srtist);

    void deleteById(Integer id);

    Artist findById(Integer id);
}

