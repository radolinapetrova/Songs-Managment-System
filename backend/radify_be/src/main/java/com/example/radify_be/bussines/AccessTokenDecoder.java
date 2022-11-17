package com.example.radify_be.bussines;

import com.example.radify_be.model.AccessToken;

public interface AccessTokenDecoder {
    public AccessToken decode(String encodedToken);
}
