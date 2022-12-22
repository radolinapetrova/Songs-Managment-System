package com.example.radify_be.persistence.impl;

import com.example.radify_be.persistence.DBRepositories.ListenersDBRepository;
import com.example.radify_be.persistence.DBRepositories.SongDBRepository;
import com.example.radify_be.persistence.DBRepositories.UserDBRepository;
import com.example.radify_be.persistence.ListenersRepo;
import com.example.radify_be.persistence.entities.ListenersEntity;
import com.example.radify_be.persistence.entities.SongEntity;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class ListenersRepoImpl implements ListenersRepo {


        private final ListenersDBRepository repo;

        private final SongDBRepository songRepo;

        private final UserDBRepository userRepo;

        @Override
        public long getMonthlyListeners(Integer id) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer monthInt = localDate.getMonthValue();
            Integer yearInt = localDate.getYear();

            String month = Integer.toString(monthInt);
            String year = Integer.toString(yearInt);

            return repo.getCount(month, year, id);
        }

        @Override
        public long getYearlyListeners(Integer id){
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer yearInt = localDate.getYear();

            String year = Integer.toString(yearInt);

            return repo.getYearlyCount(year, id);
        }

        @Override
        public void save(Integer song, Integer user){

            SongEntity songEntity = songRepo.findById(song).orElse(null);
            UserEntity userEntity = userRepo.findById(user).orElse(null);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new java.util.Date());

            repo.save(ListenersEntity.builder().song(songEntity).listener(userEntity).date(date).build());
        }
}
