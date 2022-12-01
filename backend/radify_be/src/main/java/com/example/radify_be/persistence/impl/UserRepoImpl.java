package com.example.radify_be.persistence.impl;

import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.DBRepositories.UserDBRepository;
import com.example.radify_be.persistence.UserRepo;
import com.example.radify_be.persistence.converters.UserConverter;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {
    private final UserDBRepository repo;

//    private UserEntity userEntityConverter(User user) {
//        return UserEntity.builder().username(user.getAccount().getUsername()).email(user.getAccount().getEmail()).password(user.getAccount().getPassword())
//                .role(user.getRole()).id(user.getId()).fName(user.getFName()).lName(user.getLName()).build();
//    }
//
//    private User userConverter(UserEntity user) {
//        return User.builder().id(user.getId()).role(user.getRole()).fName(user.getFName()).lName(user.getLName())
//                .account(Account.builder().username(user.getUsername()).password(user.getPassword()).email(user.getEmail()).build()).build();
//    }


    @Override
    public User save(User user) {
        return UserConverter.userConverter(repo.save(UserConverter.userEntityConverter(user)));
    }

    @Override
    public User findById(Integer id) {
        return UserConverter.userConverter(repo.findById(id).orElse(null));
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
