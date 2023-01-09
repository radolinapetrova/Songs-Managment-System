package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.controller.requests.*;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
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
    public ResponseEntity<Object> createNewPlaylist(@RequestBody CreatePlaylistRequest request) {

        Playlist pl = null;
        try{
            pl = service.createPlaylist(convert(request));
        }
        catch(InvalidInputException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        catch (UnsuccessfulAction ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
        return ResponseEntity.ok().body(pl);
    }


    @PutMapping
    public ResponseEntity<Playlist> addSongToPlaylist(@RequestBody EditPlaylistSongsRequest request) {
        Playlist pl = null;
        try{
            pl = service.addSongToPlaylist(request.getPlaylistId(), request.getSongId(), request.getUserId());
        }
        catch(UnauthorizedAction e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        catch (UnsuccessfulAction ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.ok().body(pl);
    }

    @PutMapping("/details")
    public ResponseEntity<Playlist> updatePlaylist(UpdatePlaylistRequest req){
        try{
            return ResponseEntity.ok().body(service.updatePlaylistInfo(Playlist.builder().id(req.getPlaylist()).isPublic(req.getIsPublic()).build(), req.getUser()));
        }
        catch(UnauthorizedAction er){
            return ResponseEntity.ok().body(null);
        }
    }

    @PutMapping("/remove")
    public ResponseEntity<Playlist> deleteSongsFromPlaylist(@RequestBody EditPlaylistSongsRequest request){
        Playlist pl = null;
        try{
            pl = service.removeSongsFromPlaylist(request.getPlaylistId(), request.getSongId(), request.getUserId());
        }
        catch(UnauthorizedAction e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        catch (UnsuccessfulAction ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.ok().body(pl);
    }


    @GetMapping("user/{id}")
    public ResponseEntity<List<Playlist>> getUserPlaylists(@PathVariable(value = "id") Integer id) {
        List<Playlist> playlists = service.getUserPlaylists(id);
        return ResponseEntity.ok().body(playlists);
    }

    @GetMapping("{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable(value = "id") Integer id) {
        Playlist playlist = service.findById(id);

        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok().body(playlist);
    }

    @DeleteMapping()
    public ResponseEntity<String> deletePlaylist(@RequestBody DeletePlaylistRequest request) {
        try {
            service.deletePlaylist(request.getPlaylistId(), request.getUserId());
            return ResponseEntity.ok().body("Successful deletion of the playlist");
        } catch(UnauthorizedAction e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        catch (UnsuccessfulAction ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }


    //Gets all the public playlist and all the playlist with the authenticated user as a creator
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

    private Playlist convert(CreatePlaylistRequest request) {

        //Creating a list for the users and adding the creator as the first one
        Playlist pl = Playlist.builder().isPublic(request.isPublic()).dateOfCreation(new java.util.Date()).songs(new ArrayList<>()).build();
        pl.setTitle(request.getTitle());
        pl.setCreator(User.builder().id(request.getUserId()).build());
        pl.setUsers(List.of(pl.getCreator()));
        return pl;
    }

}
