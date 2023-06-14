package com.kitten.coursera.service.impl;

import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.jwt.JwtRequest;
import com.kitten.coursera.jwt.JwtResponse;
import com.kitten.coursera.jwt.TokenProvider;
import com.kitten.coursera.service.AuthService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public JwtResponse login(JwtRequest request) {

        log.info("jwt request: {}" + request.toString());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEMail(), request.getPassword()));

        AppUser user = userService.findByEMail(request.getEMail());
        log.info("user: {}"+ user.toString());

        String accessToken = tokenProvider.createAccessToken(user.getId(), user.getEMail(), user.getRoles());
        String refreshToken = tokenProvider.createRefreshToken(user.getId(), user.getEMail());

        JwtResponse jwtResponse = JwtResponse.builder()
            .eMail(user.getEMail())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return tokenProvider.refreshUserToken(refreshToken);
    }
}
