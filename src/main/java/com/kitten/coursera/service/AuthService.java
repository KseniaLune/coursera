package com.kitten.coursera.service;

import com.kitten.coursera.jwt.JwtRequest;
import com.kitten.coursera.jwt.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest request);

    JwtResponse refresh(String refreshToken);
}
