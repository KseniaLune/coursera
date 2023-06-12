package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.entity.AppUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto mapUserToDto(AppUser user) {
    return UserDto.builder()
        .nickname(user.getNickname())
        .password(user.getPassword())
        .fullName(user.getFullName())
        .eMail(user.getEMail())
        .phone(user.getPhone())
        .build();
    }

    public List<UserDto> mapUsersToDto(List<AppUser> users) {
        return users.stream()
            .map(user -> UserDto.builder()
                .nickname(user.getNickname())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .eMail(user.getEMail())
                .phone(user.getPhone())
                .build())
            .collect(Collectors.toList());
    }

    public AppUser mapDtoToEntity(UserDto dto){
        if (dto==null){
            return null;
        }
        AppUser.AppUserBuilder user = AppUser.builder()
            .nickname(dto.getNickname())
            .password(dto.getPassword())
            .fullName(dto.getFullName())
            .eMail(dto.getEMail())
            .phone(dto.getPhone());

        return user.build();
    }
}
