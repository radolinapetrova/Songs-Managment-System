package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.ArtistService;
import com.example.radify_be.domain.Artist;
import com.example.radify_be.persistence.ArtistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepo repo;

    @Override
    public Artist findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Artist save(Artist artist) {
        return repo.save(artist);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Artist> getAll() {
        return repo.getAll();
    }
}
