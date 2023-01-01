package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongDBRepository extends JpaRepository<SongEntity, Integer>/*, PagingAndSortingRepository<SongEntity, Integer>*/ {


    List<SongEntity> findAllByTitleContaining(String title);


    List<SongEntity> findAllByPlaylistsId(Integer id);

    List<SongEntity> getAllByIdIn(List<Integer> id);

}
