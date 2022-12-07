package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistDBRepository extends JpaRepository<ArtistEntity, Integer> {


    List<ArtistEntity> findByIdIn(List<Integer> ids);
}
