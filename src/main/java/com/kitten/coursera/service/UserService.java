package com.kitten.coursera.service;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;

import java.util.UUID;

public interface UserService {
    AppUser createUser(UserDto dto);
    AppUser updateCourse(UUID id, UserDto dto);
    void deleteBy(UUID id);
}
