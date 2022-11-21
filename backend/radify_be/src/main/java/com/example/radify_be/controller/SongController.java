package com.example.radify_be.controller;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.model.requests.CreateSongRequest;
import com.example.radify_be.model.requests.GetSongByTitleRequest;
import com.example.radify_be.securityConfig.isauthenticated.IsAuthenticated;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/songs")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@IsAuthenticated
public class SongController {


    @Autowired
    SongService service;

    @PostMapping
    public ResponseEntity addSong(@RequestParam CreateSongRequest request){
        return null;
    }

    @GetMapping("{title}")
    public ResponseEntity getByTitle(@PathVariable(value="title") GetSongByTitleRequest request){
        return ResponseEntity.ok(service.getSongsByTitle(request.getTitle()));
    }
}
