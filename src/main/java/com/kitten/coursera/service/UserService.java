package com.kitten.coursera.service;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;

import java.util.List;
import java.util.UUID;

public interface UserService {

    AppUser createUser(UserDto dto);

    AppUser getById(UUID id);

    List<AppUser> findAll();

    AppUser updateUser(UUID id,UserDto dto);

    void deleteBy(UUID id);

    String signUp(UUID user_id, UUID course_id);

    List<Course> findCourseByUserId(UUID userId);

    String breakCourse(UUID userId, UUID courseId);

}
