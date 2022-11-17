package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.UserService;
import com.example.radify_be.bussines.impl.converters.DateConverter;
import com.example.radify_be.model.requests.CreatePlaylistRequest;
import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/playlists")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService service;
    private final UserService userService;


    @PostMapping
    public ResponseEntity createNewPlaylist(@RequestBody CreatePlaylistRequest request){
        //Creating a list for the users and adding the creator as the first one
        List<UserEntity> users = new ArrayList<>();
        //Retrieving all the information about the creator
        UserEntity user = userService.getById(request.getUserId());
        users.add(user);
        service.createPlaylist(PlaylistEntity.builder().title(request.getTitle()).is_public(request.isPublic()).dateOfCreation(DateConverter.convert(Calendar.getInstance().getTime())).creator(user).users(users).build());
        return ResponseEntity.status(HttpStatus.CREATED).body("Playlist created");
    }


    @GetMapping("user/{id}")
    public ResponseEntity getUserPlaylists(@PathVariable(value = "id") Integer id){
            return ResponseEntity.ok(service.getUserPlaylists(id));
    }

}
