package com.example.radify_be.bussines.impl.converters;

import com.example.radify_be.model.Song;
import com.example.radify_be.persistence.entities.SongEntity;

public class SongConverter {

    public static Song convert(SongEntity song){

        return Song.builder().id(song.getId()).title(song.getTitle()).genre(song.getGenre()).seconds(song.getSeconds()).build();

    }
}
