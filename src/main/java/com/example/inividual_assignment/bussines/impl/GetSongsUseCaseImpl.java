package com.example.inividual_assignment.bussines.impl;

import com.example.inividual_assignment.bussines.GetSongsUseCase;
import com.example.inividual_assignment.domain.GetAllSongsRequest;
import com.example.inividual_assignment.domain.GetAllSongsResponse;
import com.example.inividual_assignment.domain.Song;
import com.example.inividual_assignment.persistence.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class GetSongsUseCaseImpl implements GetSongsUseCase {

    private SongRepository songRepository;

    @Override
    public GetAllSongsResponse getSongs(final GetAllSongsRequest request) {
        List<Song> songs;

        if (StringUtils.hasText(request.getGenre())) {
            songs = songRepository.getAllByGenre(request.getGenre());
        } else {
            songs = new ArrayList<>();
        }

        final GetAllSongsResponse response = new GetAllSongsResponse();

        List<Song> results = songs
                .stream()
                .toList();
        response.setSongs(results);

        return response;

    }

}
