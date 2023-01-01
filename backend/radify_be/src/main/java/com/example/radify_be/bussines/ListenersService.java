package com.example.radify_be.bussines;

import com.example.radify_be.domain.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ListenersService {
    long getMonthlyListeners(Integer id);

    void save(Integer song, Integer user);

    long getYearlyListeners(Integer id);

    List<Song>  getTopSongs();
}
