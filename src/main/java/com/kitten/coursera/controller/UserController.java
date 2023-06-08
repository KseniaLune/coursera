package com.kitten.coursera.controller;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<AppUser>> readAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> readBy(@PathVariable("id") UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AppUser> create(@RequestBody UserDto dto){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.createUser(dto));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<AppUser> update(@PathVariable("id")UUID id,
                                          @RequestBody UserDto dto){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUser(id, dto));
    }

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
    public ResponseEntity<List<Course>>allCourses(@RequestParam("userId") UUID id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.findCourseByUserId(id));
    }



//    @GetMapping("/course/{id}")
//    public ResponseEntity<?>readCourseByUserId(@PathVariable("id") UUID id){
//        log.info("start method in controller");
//        return ResponseEntity
//            .status(HttpStatus.OK)
//            .body(userService.findCourseByUserId(id));
//    }


}
