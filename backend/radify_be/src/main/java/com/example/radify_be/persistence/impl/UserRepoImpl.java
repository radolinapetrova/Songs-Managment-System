package com.example.radify_be.persistence.impl;

import com.example.radify_be.bussines.exceptions.DublicateDataException;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.DBRepositories.UserDBRepository;
import com.example.radify_be.persistence.UserRepo;
import com.example.radify_be.persistence.converters.UserConverter;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;


@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {
    private final UserDBRepository repo;

    @Override
    public User save(User user) {
        User result = null;
        try{
            result =  UserConverter.userConverter(repo.save(UserConverter.userEntityConverter(user)));
        }
        catch (Exception e){

        }
        return result;
    }


    @Override
    public User findById(Integer id) {
        return UserConverter.userConverter(repo.findById(id).orElse(new UserEntity()));
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repo.existsById(id);
    }

    @Override
    public User findByUsername(String username) {
        return UserConverter.userConverter(repo.findByUsername(username));
    }
}
