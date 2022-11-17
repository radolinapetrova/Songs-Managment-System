package com.example.radify_be.model.requests;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetSongByTitleRequest {
    String title;
}
