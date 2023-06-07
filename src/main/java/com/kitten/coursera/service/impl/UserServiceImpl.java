package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.repo.UserRepo;
import com.kitten.coursera.service.CourseService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    @Transactional
    public AppUser createUser(UserDto dto) {
        log.info("user dto:" + dto.toString());
        var newUser = AppUser.builder()
            .nickname(dto.getNickname())
            .password(dto.getPassword())
            .fullName(dto.getFullName())
            .eMail(dto.getEMail())
            .phone(dto.getPhone())
            .build();
        log.info("newUser: " + newUser.toString());
        return userRepo.save(newUser);
    }

    @Override
    public AppUser getById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<AppUser> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public AppUser updateUser(UUID id, UserDto dto) {
        AppUser user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getEMail() != null) {
            user.setEMail(dto.getEMail());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        return userRepo.save(user);
    }

    @Override
    public void deleteBy(UUID id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<Course> findCourseByUserId(UUID userId) {
        return null;
    }

//    @Override
//    public List<Course> signUpForCourse(UUID userId, UUID courseId) {
//        AppUser user = userRepo.findById(userId).orElseThrow();
//        Course course = courseService.findBy(courseId);
//
//        user.getCourses().add(course);
//        course.getUsers().add(user);
//
//        userRepo.save(user);
//        courseService.update(course);
//
//        return user.getCourses().stream()
//            .toList();
//    }
//
//    @Override
//    public void testMethod(UUID id) {
//
//    }
//
//
//    @Override
//    public AppUser updateCourse(UUID id, UserDto dto) {
//        var userApp = findBy(id).orElseThrow();
//
//        userApp.setNickname(dto.getNickname());
//        userApp.setPassword(dto.getPassword());
//        userApp.setFullName(dto.getFullName());
//        userApp.setEMail(dto.getEMail());
//        userApp.setPhone(dto.getPhone());
//
//        return userRepo.save(userApp);
//    }
//
//    @Override
//    public AppUser getById(UUID id) {
//        return userRepo.findById(id).orElseThrow();
//    }
//
//
//
//
//
//    @Override
//    public Set<Course> findCourseByUserId(UUID id) {
//        log.info("try to find users");
//        AppUser user = userRepo.findById(id).get();
//        log.info("user = {}", user);
//        log.info("user course = {}", user.getCourses().toString());
//        Set<Course> courses = user.getCourses();
//        return courses;
//    }
//
}
