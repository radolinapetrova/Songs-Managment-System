package com.example.radify_be.domain;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private Integer id;
    private String title;
    private boolean isPublic;
    private Date dateOfCreation;
    private User creator;
    private List<User> users;
    private List<Song> songs;

    public void setTitle(String title) {
        if (title == null){
            throw new InvalidInputException();
        }
        this.title = title;
    }

    public void setCreator(User creator){
        if(creator == null){
            throw new InvalidInputException();
        }
        this.creator = creator;
    }
}
