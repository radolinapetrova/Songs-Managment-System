package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDBRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username);


}
