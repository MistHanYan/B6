package com.example.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {

    public final String signKey = "Mist";
    public void getJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))
                .compact();
        System.out.println(jwt);
    }


    public void parseJwt(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiTWlzdCIsImlkIjoxLCJleHAiOjE2ODgwMzk0NjB9._I4JSTYnXUluTY3kdO-BS1LAaI3WMnMXzBVHL5ziDKY")
                .getBody();
    }
}
