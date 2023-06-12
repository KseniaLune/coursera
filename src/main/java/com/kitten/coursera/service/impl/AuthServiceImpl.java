package com.kitten.coursera.service.impl;

import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.jwt.JwtRequest;
import com.kitten.coursera.jwt.JwtResponse;
import com.kitten.coursera.jwt.TokenProvider;
import com.kitten.coursera.service.AuthService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public JwtResponse login(JwtRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEMail(), request.getPassword()));

        AppUser user = userService.findByEMail(request.getEMail());

        String accessToken = tokenProvider.createAccessToken(user.getId(), user.getEMail());
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
//        return tokenProvider.refreshUserToken(refreshToken);
        return null;
    }
}
