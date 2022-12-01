package com.example.radify_be.bussines;

import com.example.radify_be.domain.Artist;

public interface ArtistService {

    Artist findById(Integer id);

    Artist save(Artist artist);

    void deleteById(Integer id);
}

