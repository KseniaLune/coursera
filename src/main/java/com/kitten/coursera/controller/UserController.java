package com.kitten.coursera.controller;

import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable("id") UUID id) {
        userService.deleteBy(id);
        return ResponseEntity.ok("Юзер успешно удален");
    }

}
