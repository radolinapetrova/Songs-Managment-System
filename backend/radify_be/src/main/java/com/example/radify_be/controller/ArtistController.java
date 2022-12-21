package com.example.radify_be.controller;


import com.example.radify_be.bussines.ArtistService;
import com.example.radify_be.domain.Artist;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ArtistController {

    private ArtistService service;

    @GetMapping("/all")
    public ResponseEntity<List<Artist>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }
}
