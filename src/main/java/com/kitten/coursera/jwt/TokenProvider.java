package com.kitten.coursera.jwt;

import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Role;
import com.kitten.coursera.exeption.AccessDeniedEx;
import com.kitten.coursera.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(UUID userId, String eMail, Set<Role>roles) {
        Claims claims = Jwts.claims().setSubject(eMail);
        claims.put("id", userId);
        claims.put("roles", resolveRoles(roles));
        Instant validity = Instant.now()
            .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(validity))
            .signWith(key)
            .compact();
    }
    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
            .map(Role::getRole)
            .map(Enum::name)
            .collect(Collectors.toList());
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

    public JwtResponse refreshUserToken(String refreshToken) {
        if(!validateToken(refreshToken)){
            throw new AccessDeniedEx();
        }
        UUID userId = UUID.fromString(getId(refreshToken));
        AppUser user = userService.getById(userId);
        String newAccessToken = createAccessToken(userId, user.getEMail(), user.getRoles());
        String newRefreshToken = createRefreshToken(userId, user.getEMail());

        return JwtResponse.builder()
            .eMail(user.getEMail())
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .build();
    }

    public boolean validateToken(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new java.util.Date());
    }

    private String getId(String token){
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody().get("id")
            .toString();
    }

    public Authentication getAuthentication(String bearerToken) {
        String username = getUsername(bearerToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }
    private String getUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
