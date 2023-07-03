package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

public class JwtTest {

    @Test
    public void getJwt(){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("phoneNum","17726639096");
        //claims.put("name","Mist");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"Mist")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void parseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("Mist")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiTWlzdCIsImlkIjoxLCJleHAiOjE2ODgwMzk0NjB9._I4JSTYnXUluTY3kdO-BS1LAaI3WMnMXzBVHL5ziDKY")
                .getBody();
        System.out.println(claims);
    }
}
