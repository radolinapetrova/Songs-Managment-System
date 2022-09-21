package com.example.inividual_assignment.bussines;

import com.example.inividual_assignment.domain.GetAllSongsRequest;
import com.example.inividual_assignment.domain.GetAllSongsResponse;

public interface GetSongsUseCase {
    GetAllSongsResponse getSongs(final GetAllSongsRequest request);
}
