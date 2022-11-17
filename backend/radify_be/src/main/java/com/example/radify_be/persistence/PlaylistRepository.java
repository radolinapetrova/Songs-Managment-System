package com.example.radify_be.persistence;


import com.example.radify_be.persistence.entities.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {

    //public List<PlaylistEntity> findByTitle(String title, Pageable pageable);

    public List<PlaylistEntity> getReferencesByUsersId(Integer userId);
}
