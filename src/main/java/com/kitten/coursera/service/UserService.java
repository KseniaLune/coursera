package com.kitten.coursera.service;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public List<AppUser> readAllUsers() {
        return userRepo.findAll();
    }

    public Optional<AppUser> findBy(Long id) {
        return userRepo.findById(id);
    }

    public AppUser createUser(UserDto dto) {
        var newUser = AppUser.builder()
            .nickname(dto.getNickname())
            .password(dto.getPassword())
            .fullName(dto.getFullName())
            .eMail(dto.getEMail())
            .phone(dto.getPhone())
            .build();
        return userRepo.save(newUser);
    }

    public AppUser updateCourse(Long id, UserDto dto) {
        var userApp = findBy(id).orElseThrow();

        userApp.setNickname(dto.getNickname());
        userApp.setPassword(dto.getPassword());
        userApp.setFullName(dto.getFullName());
        userApp.setEMail(dto.getEMail());
        userApp.setPhone(dto.getPhone());

        return userRepo.save(userApp);
    }

    public void deleteBy(Long id) {
        userRepo.deleteById(id);
    }
}
