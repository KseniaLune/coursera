package com.kitten.coursera.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(UUID userId, String eMail) {
        Claims claims = Jwts.claims().setSubject(eMail);
        claims.put("id", userId);
        Instant validity = Instant.now()
            .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(validity))
            .signWith(key)
            .compact();
    }

    public String createRefreshToken(UUID userId, String eMail) {
        Claims claims = Jwts.claims().setSubject(eMail);
        claims.put("id", userId);
        Instant validity = Instant.now()
            .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(java.util.Date.from(validity))
            .signWith(key)
            .compact();
    }

    public JwtResponse refreshUserToken(String refreshToken) throws AccessDeniedException {
        if(!validateToken(refreshToken)){
            throw new AccessDeniedException("Token is not valid");
        }

        return null;
    }

    public boolean validateToken(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new java.util.Date());
    }
}
