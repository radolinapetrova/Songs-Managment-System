package com.example.radify_be.persistence;

import java.util.List;

public interface ListenersRepo {
    long getMonthlyListeners(Integer id);
    void save(Integer song, Integer user);

    long getAvgListeners(Integer id);
    List<Long> getTopSongs();
}
