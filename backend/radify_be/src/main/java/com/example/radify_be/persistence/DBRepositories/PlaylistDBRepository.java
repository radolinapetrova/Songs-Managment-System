package com.example.radify_be.persistence.DBRepositories;


import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistDBRepository extends JpaRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {



    @Modifying
    @Query("delete from PlaylistEntity p where p.id = :id")
    void deleteById(@Param("id")Integer id);

    List<PlaylistEntity> getReferencesByUsersId(Integer id);

    List<PlaylistEntity> getReferencesByUsersIdOrIsPublic(Integer id, boolean isPublic);


    @Query(value = "select * from playlists where title like concat('%',:title,'%') and (creator_id = :id or is_public = :isPublic)", nativeQuery = true)
    List<PlaylistEntity> getReferencesByTitleContainingOrUsersIdOrIsPublic(@Param("title") String title, @Param("id")Integer id,@Param("isPublic") boolean isPublic);



    boolean existsPlaylistByCreator(UserEntity creator);

}
