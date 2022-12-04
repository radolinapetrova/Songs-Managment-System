package com.example.radify_be.persistence.DBRepositories;


import com.example.radify_be.persistence.entities.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistDBRepository extends JpaRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {


    List<PlaylistEntity> getReferencesByUsersId(Integer id);

    List<PlaylistEntity> getReferencesByUsersIdOrIsPublic(Integer id, boolean isPublic);

    @Query("SELECT p from PlaylistEntity p WHERE p.isPublic = true OR p.users p.title LIKE %:title%")
    List<PlaylistEntity> getReferencesByUsersIdOrIsPublicAndTitle(Integer id, boolean isPublic, String title);




}
