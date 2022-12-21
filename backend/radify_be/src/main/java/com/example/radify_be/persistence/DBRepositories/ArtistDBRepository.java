package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistDBRepository extends JpaRepository<ArtistEntity, Integer> {


    List<ArtistEntity> findByIdIn(List<Integer> ids);

    //List<ArtistEntity> getAllByfNameLikeOrlNameLike(String fName, Optional<String> lName);

}
