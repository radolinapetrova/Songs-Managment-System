package com.example.inividual_assignment.controller;

import com.example.inividual_assignment.bussines.GetSongUseCase;
import com.example.inividual_assignment.bussines.GetSongsUseCase;
import com.example.inividual_assignment.domain.GetAllSongsRequest;
import com.example.inividual_assignment.domain.GetAllSongsResponse;
import com.example.inividual_assignment.domain.Song;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
@AllArgsConstructor


public class SongController {

    private final GetSongsUseCase getSongsUseCase;
    private final GetSongUseCase getSongUseCase;

    @GetMapping
    public ResponseEntity<GetAllSongsResponse> getAllSongs(@RequestParam(value = "genre", required = false) final String genre) {
        GetAllSongsRequest request = GetAllSongsRequest.builder().genre(genre).build();
        GetAllSongsResponse response = getSongsUseCase.getSongs(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Song> getSong(@PathVariable(value = "id") final long songId) {
        final Song song = getSongUseCase.getSong(songId);

        if (song.equals(null)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(song);
        }
    }
}
