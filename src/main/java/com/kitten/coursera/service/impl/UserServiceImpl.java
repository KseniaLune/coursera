package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.repo.UserRepo;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public List<AppUser> readAllUsers() {
        return userRepo.findAll();
    }

    public Optional<AppUser> findBy(UUID id) {
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

    @Override
    public AppUser updateCourse(UUID id, UserDto dto) {
        var userApp = findBy(id).orElseThrow();

        userApp.setNickname(dto.getNickname());
        userApp.setPassword(dto.getPassword());
        userApp.setFullName(dto.getFullName());
        userApp.setEMail(dto.getEMail());
        userApp.setPhone(dto.getPhone());

        return userRepo.save(userApp);
    }


    public void deleteBy(UUID id) {
        userRepo.deleteById(id);
    }
}
