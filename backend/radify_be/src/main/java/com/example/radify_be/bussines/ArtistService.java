package com.example.radify_be.bussines;

import com.example.radify_be.domain.Artist;

import java.util.List;

public interface ArtistService {

    Artist findById(Integer id);

    Artist save(Artist artist);

    void deleteById(Integer id);

    List<Artist> getAll();
}

