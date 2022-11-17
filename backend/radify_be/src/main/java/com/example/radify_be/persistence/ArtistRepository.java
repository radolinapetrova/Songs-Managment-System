package com.example.radify_be.persistence;

import com.example.radify_be.persistence.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Integer> {
}
