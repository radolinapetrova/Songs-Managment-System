package com.example.radify_be.controller;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.model.requests.CreateSongRequest;
import com.example.radify_be.model.requests.GetSongByTitleRequest;
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
        return null;
    }

    @GetMapping
    public ResponseEntity getByTitle(@RequestParam GetSongByTitleRequest request){
        return ResponseEntity.ok(service.getSongsByTitle(request.getTitle()));
    }
}
