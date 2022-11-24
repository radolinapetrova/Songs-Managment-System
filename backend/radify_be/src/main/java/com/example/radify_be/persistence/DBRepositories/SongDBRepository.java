package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongDBRepository extends JpaRepository<SongEntity, Integer>, PagingAndSortingRepository<SongEntity, Integer> {

    @Query("SELECT s from SongEntity s WHERE s.title LIKE %:title%")
    public List<SongEntity> findAllByTitle(String title, Pageable pageable);
}
