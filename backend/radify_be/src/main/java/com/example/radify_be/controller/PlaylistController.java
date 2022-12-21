package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.controller.requests.AddSongRequest;
import com.example.radify_be.controller.requests.GetPlaylistsByTitleAndUser;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
import com.example.radify_be.controller.requests.CreatePlaylistRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/playlists")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService service;


    @PostMapping
    public ResponseEntity<String> createNewPlaylist(@RequestBody CreatePlaylistRequest request) {
        //Creating a list for the users and adding the creator as the first one

        User user = User.builder().id(request.getUserId()).build();

        service.createPlaylist(convert(request, user, List.of(user)));
        return ResponseEntity.status(HttpStatus.CREATED).body("Playlist created");
    }


    @PutMapping
    public ResponseEntity<String> addSongToPlaylist(@RequestBody AddSongRequest request) {
        service.addSongToPlaylist(request.getPlaylistId(), request.getSongId());
        return ResponseEntity.ok().body("Idk if it worked tbh");
    }


    @DeleteMapping
    public ResponseEntity<String> deleteSongsFromPlaylist(@RequestBody AddSongRequest request){
        service.removeSongsFromPlaylist(request.getPlaylistId(), request.getSongId());
        return ResponseEntity.ok().body("Idk if it worked tbh");
    }


    @GetMapping("user/{id}")
    public ResponseEntity<List<Playlist>> getUserPlaylists(@PathVariable(value = "id") Integer id) {
        List<Playlist> playlists = service.getUserPlaylists(id);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable(value = "id") Integer id) {
        Playlist playlist = service.findById(id);

        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok().body(playlist);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable(value = "id") Integer id) {
        try {
            service.deletePlaylist(id);
            return ResponseEntity.ok().body("Successful deletion of the playlist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping("all/{userId}")
    public ResponseEntity<List<Playlist>> getAllPlaylists(@PathVariable(value = "userId") Integer userId){
        List<Playlist> playlists = service.getAllPublicAndUser(userId);

        if (playlists.isEmpty()){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(playlists);
    }

    @PostMapping("title")
    public ResponseEntity<List<Playlist>> getAllByTitle(@RequestBody GetPlaylistsByTitleAndUser request){
        List<Playlist> playlists = service.getAllByTitle(request.getId(), request.getTitle());

        if (playlists.isEmpty()){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(playlists);
    }

    private Playlist convert(CreatePlaylistRequest request, User user, List<User> users) {
        return Playlist.builder().title(request.getTitle()).isPublic(request.isPublic()).dateOfCreation(new java.util.Date()).songs(new ArrayList<>()).creator(user).users(users).build();
    }

}
