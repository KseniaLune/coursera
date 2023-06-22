package com.kitten.coursera.controller;

import com.kitten.coursera.domain.entity.AppUser;
import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.dto.mapper.UserMapper;
import com.kitten.coursera.jwt.JwtRequest;
import com.kitten.coursera.jwt.JwtResponse;
import com.kitten.coursera.service.AuthService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/sign_in")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        AppUser newUser = userService.createUser(userDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.toDto(newUser));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody String refreshToken) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.refresh(refreshToken));
    }

}
