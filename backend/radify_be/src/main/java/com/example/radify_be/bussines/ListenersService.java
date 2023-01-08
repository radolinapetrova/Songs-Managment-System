package com.example.radify_be.bussines;

import com.example.radify_be.domain.Song;

import java.util.List;

public interface ListenersService {
    long getMonthlyListeners(Integer id);

    void save(Integer song, Integer user);

    long getYearlyListeners(Integer id);

    List<Song>  getTopSongs();
}
