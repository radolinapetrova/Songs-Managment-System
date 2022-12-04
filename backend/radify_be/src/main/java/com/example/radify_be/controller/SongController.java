package com.example.radify_be.controller;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.controller.requests.CreateSongRequest;
import com.example.radify_be.domain.Song;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/songs")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class SongController {

    @Autowired
    SongService service;

    @PostMapping
    public ResponseEntity addSong(@RequestBody CreateSongRequest request) {
        return ResponseEntity.ok(service.createSong(convert(request)));
    }

    @GetMapping()
    public ResponseEntity<List<Song>> getAllSongs(){
        List<Song> songs = service.getAllSongs();
        return ResponseEntity.ok().body(songs);
    }


    @GetMapping("{title}")
    public ResponseEntity getByTitle(@PathVariable(value = "title") String title) {
        return ResponseEntity.ok(service.getSongsByTitle(title));
    }


    @GetMapping("playlist/{id}")
    public ResponseEntity getPlaylistSongs(@PathVariable(value = "id") Integer id) {
            List<Song> songs = service.getAllPlaylistSongs(id);

            if (songs.size() == 0){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(":(");
            }
            return ResponseEntity.ok().body(songs);

    }

    private Song convert(CreateSongRequest request) {

        //return Song.builder().title(request.getTitle()).genre(request.getGenre()).seconds(request.getSeconds()).artists(request.getArtistsIds()).build();
        return null;
    }
}
