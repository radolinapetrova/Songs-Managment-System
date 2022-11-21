package com.example.radify_be.persistence;

import com.example.radify_be.persistence.entities.SongEntity;
import com.example.radify_be.securityConfig.isauthenticated.IsAuthenticated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface SongRepository extends JpaRepository<SongEntity, Integer>, PagingAndSortingRepository<SongEntity, Integer> {

    @Query("SELECT s from SongEntity s WHERE s.title LIKE %:title%")
    public List<SongEntity> findAllByTitle(String title, Pageable pageable);
}
