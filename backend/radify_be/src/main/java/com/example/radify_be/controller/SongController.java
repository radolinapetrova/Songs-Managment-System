package com.example.radify_be.controller;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.controller.requests.CreateSongRequest;
import com.example.radify_be.domain.Song;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/songs")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class SongController {


    @Autowired
    SongService service;

    @PostMapping
    public ResponseEntity addSong(@RequestParam CreateSongRequest request){
        return ResponseEntity.ok(service.createSong(convert(request)));
    }



    @GetMapping("title")
    public ResponseEntity getByTitle(@RequestParam String title){
        return ResponseEntity.ok(service.getSongsByTitle(title));
    }


    private Song convert(CreateSongRequest request){
        return Song.builder().title(request.getTitle()).genre(request.getGenre()).seconds(request.getSeconds()).artists(request.getArtists()).build();
    }
}
