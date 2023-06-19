package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.domain.entity.UserAvatar;
import com.kitten.coursera.dto.UserAvatarDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAvatarMapper implements Mappable <UserAvatar, UserAvatarDto>{
    @Override
    public UserAvatarDto toDto(UserAvatar entity) {
        UserAvatarDto dto = UserAvatarDto.builder()
            .file(entity.getFile())
            .build();
        return dto;
    }

    @Override
    public List<UserAvatarDto> toDto(List<UserAvatar> entities) {
        return null;
    }

    @Override
    public UserAvatar toEntity(UserAvatarDto dto) {
        UserAvatar userAvatar = UserAvatar.builder()
            .file(dto.getFile())
            .build();
        return userAvatar;
    }

    @Override
    public List<UserAvatar> toEntity(List<UserAvatarDto> dtos) {
        return null;
    }
}
