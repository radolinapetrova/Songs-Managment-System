package com.example.radify_be.persistence;

import java.util.HashMap;
import java.util.List;

public interface ListenersRepo {
    long getMonthlyListeners(Integer id);
    void save(Integer song, Integer user);

    long getYearlyListeners(Integer id);
}
