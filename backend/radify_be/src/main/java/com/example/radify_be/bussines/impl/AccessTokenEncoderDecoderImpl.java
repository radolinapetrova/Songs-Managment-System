package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.AccessTokenDecoder;
import com.example.radify_be.bussines.AccessTokenEncoder;
import com.example.radify_be.model.AccessToken;
import com.example.radify_be.model.Role;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {

    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("$127usdhf87w3if4cmce89vm3woe84yv89ue9umkjhf9") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public AccessToken decode(String encodedToken) {
        //Parsing the String token to a Jwt Object(so methods for this Object can be used)
        Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(encodedToken);
        Claims claims = (Claims) jwt.getBody();

        return AccessToken.builder()
                .role(claims.get("role", Role.class))
                .subject(claims.getSubject())
                .userId(claims.get("userId", Integer.class))
                .build();
    }

    @Override
    public String encode(AccessToken token) {
        Map<String, Object> claims = new HashMap<>();

        if(!token.getRole().equals(null)){
            claims.put("role", token.getRole());
        }

        if (!token.getUserId().equals(null)){
            claims.put("userId", token.getUserId());
        }


        return Jwts.builder()
                .setSubject(token.getSubject())
                .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                .addClaims(claims)
                .signWith(key)
                .compact();
    }
}
