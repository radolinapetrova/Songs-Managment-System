package com.example.radify_be.controller;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.controller.requests.CreateSongRequest;
import com.example.radify_be.controller.requests.DeleteSongRequest;
import com.example.radify_be.domain.Artist;
import com.example.radify_be.domain.Song;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/songs")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@Slf4j
public class SongController {

    private final SongService service;

    @PostMapping
    public ResponseEntity addSong(@RequestBody CreateSongRequest request) {
        return ResponseEntity.ok().body(service.createSong(convert(request)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = service.getAllSongs();
        return ResponseEntity.ok().body(songs);
    }


    @GetMapping("title/{title}")
    public ResponseEntity getByTitle(@PathVariable(value = "title") String title) {
        return ResponseEntity.ok(service.getSongsByTitle(title));
    }


    @GetMapping("playlist/{id}")
    public ResponseEntity getPlaylistSongs(@PathVariable(value = "id") Integer id) {
        List<Song> songs = service.getAllPlaylistSongs(id);

        log.info("Size is {}", songs.size());
        if (songs.size() == 0) {

            return ResponseEntity.ok().body(new ArrayList<Song>());
        }
        return ResponseEntity.ok().body(songs);

    }

    @DeleteMapping()
    public ResponseEntity deleteSong(@RequestBody DeleteSongRequest request){
        log.info("Hereee");
        service.deleteSong(request.songId, request.getUserId());

        return ResponseEntity.ok().body("Noice");
    }


    @GetMapping("{id}")
    public ResponseEntity getSongById(@PathVariable(value = "id") Integer id) {
        Song song = service.getById(id);
        if (song != null) {

            return ResponseEntity.ok().body(song);
        }
        return ResponseEntity.ok().body("Nooooo");
    }

    private Song convert(CreateSongRequest request) {
        List<Artist> artists = request.getArtistsIds().stream().map(id -> Artist.builder().id(id).build()).collect(Collectors.toList());

        return Song.builder().title(request.getTitle()).genre(request.getGenre()).seconds(request.getSeconds()).artists(artists).build();
    }
}
