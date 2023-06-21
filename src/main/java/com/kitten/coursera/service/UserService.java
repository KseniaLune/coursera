package com.kitten.coursera.service;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.entity.UserAvatar;
import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.domain.entity.AppUser;
import com.kitten.coursera.domain.entity.Course;
import com.kitten.coursera.domain.entity.Role;

import java.util.List;
import java.util.UUID;

public interface UserService {

    AppUser createUser(UserDto dto);

    AppUser getById(UUID id);

    List<AppUser> findAll();

    AppUser updateUser(UUID id,UserDto dto);

    ResponseJson deleteBy(UUID id);

    ResponseJson signUp(UUID userId, UUID courseId);

    List<Course> findCourseByUserId(UUID userId);

    ResponseJson breakCourse(UUID userId, UUID courseId);

    AppUser findByEMail(String eMail);

    ResponseJson addNewRole(UUID id, Role.RoleName role);

    ResponseJson uploadAvatar(UUID userId, UserAvatar avatar);

    ResponseJson showAvatar(UUID userId);
}
