package com.example.radify_be.bussines;

import com.example.radify_be.model.AccessToken;

public interface AccessTokenEncoder {

    public String encode(AccessToken token);


}
