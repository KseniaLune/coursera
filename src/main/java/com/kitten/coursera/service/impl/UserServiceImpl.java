package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.entity.UserToCourse;
import com.kitten.coursera.repo.UserRepo;
import com.kitten.coursera.repo.UserToCourseRepo;
import com.kitten.coursera.service.CourseService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final CourseService courseService;
    private final UserToCourseRepo userCourseRepo;

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
    public String signUp(UUID user_id, UUID course_id) {
        String result;
        if(userCourseRepo.findByUserIdAndCourseId(user_id, course_id)==null){
            userRepo.signUp(user_id, course_id);
            result = "It's OK";
        }else{
            result = "There is a problem, return later";
        }
        return result;
    }

    @Override
    public List<Course> findCourseByUserId(UUID userId) {
        List<Course> courses = userRepo.findCourseByUserId(userId);
        return courses;
    }

    @Override
    public String breakCourse(UUID userId, UUID courseId) {
        String result;
        UserToCourse userToCourse = userCourseRepo.findByUserIdAndCourseId(userId, courseId);
        if(userToCourse!=null){
            userCourseRepo.delete(userToCourse);
            result = "You leave the course";
        }else{
            result="There is a problem, return later";
        }
        return result;
    }


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
