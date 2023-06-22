package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.domain.entity.AppUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mappable<AppUser, UserDto> {

    public UserDto toDto(AppUser user) {
    return UserDto.builder()
        .nickname(user.getNickname())
        .password(user.getPassword())
        .fullName(user.getFullName())
        .eMail(user.getEMail())
        .phone(user.getPhone())
        .avatar(user.getAvatar())
        .build();
    }

    public List<UserDto> toDto(List<AppUser> users) {
        return users.stream()
            .map(user -> UserDto.builder()
                .nickname(user.getNickname())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .eMail(user.getEMail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .build())
            .collect(Collectors.toList());
    }

    public AppUser toEntity(UserDto dto){
        if (dto==null){
            return null;
        }
        AppUser.AppUserBuilder user = AppUser.builder()
            .nickname(dto.getNickname())
            .password(dto.getPassword())
            .fullName(dto.getFullName())
            .eMail(dto.getEMail())
            .phone(dto.getPhone())
            .roles(new HashSet<>());

        return user.build();
    }

    @Override
    public List<AppUser> toEntity(List<UserDto> dtos) {
        return null;
    }
}
