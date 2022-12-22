package com.example.radify_be.bussines;

import java.util.HashMap;
import java.util.List;

public interface ListenersService {
    long getMonthlyListeners(Integer id);

    void save(Integer song, Integer user);

    long getYearlyListeners(Integer id);
}
