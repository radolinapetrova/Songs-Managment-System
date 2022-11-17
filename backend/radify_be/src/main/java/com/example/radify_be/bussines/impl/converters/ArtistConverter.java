package com.example.radify_be.bussines.impl.converters;

import com.example.radify_be.model.Artist;
import com.example.radify_be.persistence.entities.ArtistEntity;

public class ArtistConverter {


    public static Artist convert (ArtistEntity artist){
        return Artist.builder().id(artist.getId()).lName(artist.getLname()).fName(artist.getFName()).build();
    }
}
