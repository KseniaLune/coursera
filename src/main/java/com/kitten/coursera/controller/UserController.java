package com.kitten.coursera.controller;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.entity.Role;
import com.kitten.coursera.domain.entity.UserAvatar;
import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.dto.UserAvatarDto;
import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.dto.mapper.CourseMapper;
import com.kitten.coursera.dto.mapper.UserAvatarMapper;
import com.kitten.coursera.dto.mapper.UserMapper;
import com.kitten.coursera.service.UserAvatarService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final UserAvatarMapper userAvatarMapper;

    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    @GetMapping
    public ResponseEntity<List<UserDto>> readAll(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.toDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> readBy(@PathVariable("id") UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.toDto(userService.getById(id)));
    }

    //TODO:реализовать смену пароля
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> update(@PathVariable("id")UUID id,
                                          @RequestBody UserDto dto){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.toDto(userService.updateUser(id, dto)));
    }

    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/{id}/add_role")
    public  ResponseEntity<ResponseJson> addNewRole(@PathVariable("id")UUID id,
                                                    @RequestBody String roleString){
        Role.RoleName role = Role.RoleName.valueOf(roleString);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.addNewRole(id, role));
    }
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    @GetMapping("/sign_up")
    public ResponseEntity<ResponseJson> signUpToCourse(@RequestParam("userId") UUID userId,
                                                 @RequestParam("courseId") UUID courseId){
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.signUp(userId, courseId));
    }

    @GetMapping("/break_course")
    public ResponseEntity<ResponseJson> breakCourse(@RequestParam("userId") UUID userId,
                                              @RequestParam("courseId") UUID courseId){
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.breakCourse(userId, courseId));

    }

    @GetMapping("/all_courses")
    public ResponseEntity<List<CourseDto>>readAllCourses(@RequestParam("userId") UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseMapper.toDto(userService.findCourseByUserId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseJson> deleteUsers(@PathVariable("id") UUID id) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.deleteBy(id));
    }
    //TODO:для методов досту админ/владелец либо юзер сам себя


    @PostMapping("/{id}/add_avatar")
    public ResponseEntity<ResponseJson> addNewAvatar(@PathVariable("id") UUID userId,
                                          @ModelAttribute @Validated UserAvatarDto avaDto){

        UserAvatar avatar = userAvatarMapper.toEntity(avaDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.uploadAvatar(userId, avatar));
    }

    @GetMapping("/{user_id}/get_avatar")
    public ResponseEntity<ResponseJson> getAvatar (@PathVariable("user_id") UUID userId){
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.showAvatar(userId));
    }
}
