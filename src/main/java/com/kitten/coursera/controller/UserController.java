package com.kitten.coursera.controller;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.dto.mapper.CourseMapper;
import com.kitten.coursera.dto.mapper.UserMapper;
import com.kitten.coursera.entity.Role;
import com.kitten.coursera.repo.UserRepo;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    private final UserRepo userRepo;

    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    @GetMapping
    public ResponseEntity<List<UserDto>> readAll(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.mapUsersToDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> readBy(@PathVariable("id") UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.mapUserToDto(userService.getById(id)));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<UserDto> update(@PathVariable("id")UUID id,
                                          @RequestBody UserDto dto){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userMapper.mapUserToDto(userService.updateUser(id, dto)));
    }

    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/{id}/add_role")
    public  ResponseEntity<String> addNewRole(@PathVariable("id")UUID id,
                                              @RequestBody String roleString){
        Role.RoleName role = Role.RoleName.valueOf(roleString);
       String result = userService.addNewRole(id, role);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable("id") UUID id) {
        userService.deleteBy(id);
        return ResponseEntity.ok("Юзер успешно удален");
    }

    @GetMapping("/sign_up")
    public ResponseEntity<String> signUpToCourse(@RequestParam("userId") UUID userId,
                                                 @RequestParam("courseId") UUID courseId){
      String result =  userService.signUp(userId, courseId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/breakCourse")
    public ResponseEntity<String> breakCourse(@RequestParam("userId") UUID userId,
                                              @RequestParam("courseId") UUID courseId){
        String result = userService.breakCourse(userId, courseId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/allCourses")
    public ResponseEntity<List<CourseDto>>readAllCourses(@RequestParam("userId") UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseMapper.mapCoursesToDto(userService.findCourseByUserId(id)));
    }
}
