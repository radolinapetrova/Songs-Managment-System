package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.ListenersService;
import com.example.radify_be.persistence.ListenersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenersServiceImpl implements ListenersService {

    private final ListenersRepo repo;

    @Override
    public long getMonthlyListeners(Integer id){
        return repo.getMonthlyListeners(id);
    }

    @Override
    public long getYearlyListeners(Integer id){
        return repo.getYearlyListeners(id);
    }

    @Override
    public void save(Integer song, Integer user){
        repo.save(song, user);
    }
}


