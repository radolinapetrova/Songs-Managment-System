package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.UserService;
import com.example.radify_be.controller.requests.AddSongRequest;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;
import com.example.radify_be.domain.User;
import com.example.radify_be.controller.requests.CreatePlaylistRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/playlists")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService service;
    private final UserService userService;


    @PostMapping
    public ResponseEntity createNewPlaylist(@RequestBody CreatePlaylistRequest request) {
        //Creating a list for the users and adding the creator as the first one
        List<User> users = new ArrayList<>();
        //Retrieving all the information about the creator
        User user = userService.getById(request.getUserId());
        users.add(user);
        service.createPlaylist(convert(request, user, users));
        return ResponseEntity.status(HttpStatus.CREATED).body("Playlist created");
    }


    @PutMapping
    public ResponseEntity addSongToPlaylist(@RequestBody AddSongRequest request) {
        service.addSongToPlaylist(service.findById(request.getPlaylistId()), Song.builder().id(request.getSongId()).build());
        return ResponseEntity.ok().body("Idk if it worked tbh");
    }


    @GetMapping("user/{id}")
    public ResponseEntity getUserPlaylists(@PathVariable(value = "id") Integer id) {
        List<Playlist> playlists = service.getUserPlaylists(id);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("{id}")
    public ResponseEntity getPlaylistById(@PathVariable(value = "id") Integer id) {
        Playlist playlist = service.findById(id);

        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Orr nohrrr");
        }
        return ResponseEntity.ok().body(playlist);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletePlaylist(@PathVariable(value = "id") Integer id) {
        try {
            service.deletePlaylist(id);
            return ResponseEntity.ok().body("Successful deletion of the playlist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping("all/{userId}")
    public ResponseEntity getAllPlaylists(@PathVariable(value = "userId") Integer userId){
        List<Playlist> playlists = service.getAllPublicAndUser(userId);

        if (playlists.isEmpty()){
            return ResponseEntity.ok().body("No playlists, or u did smth wronggg");
        }
        return ResponseEntity.ok().body(playlists);
    }

    private Playlist convert(CreatePlaylistRequest request, User user, List<User> users) {
        return Playlist.builder().title(request.getTitle()).isPublic(request.isPublic()).dateOfCreation(new java.util.Date()).songs(new ArrayList<>()).creator(user).users(users).build();
    }


}
