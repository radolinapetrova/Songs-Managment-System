package com.example.radify_be.persistence;

import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username);


}
