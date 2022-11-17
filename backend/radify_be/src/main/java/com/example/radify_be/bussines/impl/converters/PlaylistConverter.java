package com.example.radify_be.bussines.impl.converters;

import com.example.radify_be.model.Playlist;
import com.example.radify_be.persistence.entities.PlaylistEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlaylistConverter {


    public static Playlist convert(PlaylistEntity playlist) {

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(playlist.getDateOfCreation());
        } catch (ParseException e) {

        }

        return Playlist.builder().id(playlist.getId()).isPublic(playlist.is_public()).title(playlist.getTitle()).creator(UserConverter.convert(playlist.getCreator())).dateOfCreation(date)./*users(playlist.getUsers().stream().map(UserConverter::convert).collect(Collectors.toList())).*/build();
    }


}
