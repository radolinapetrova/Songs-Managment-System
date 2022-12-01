package com.example.radify_be.persistence.converters;

import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.UserEntity;

import java.util.ArrayList;

public class UserConverter {
    public static UserEntity userEntityConverter(User user) {
        return UserEntity.builder().username(user.getAccount().getUsername()).email(user.getAccount().getEmail()).password(user.getAccount().getPassword())
                .role(user.getRole()).id(user.getId()).fName(user.getFName()).lName(user.getLName()).playlists(new ArrayList<PlaylistEntity>()).build();
    }

    public static User userConverter(UserEntity user) {
        return User.builder().id(user.getId()).role(user.getRole()).fName(user.getFName()).lName(user.getLName())
                .account(Account.builder().username(user.getUsername()).password(user.getPassword()).email(user.getEmail()).build()).build();
    }

}
