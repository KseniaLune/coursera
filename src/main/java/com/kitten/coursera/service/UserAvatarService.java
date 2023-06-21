package com.kitten.coursera.service;

import com.kitten.coursera.domain.entity.UserAvatar;

public interface UserAvatarService {
    String addNewAvatar(UserAvatar ava);
     String showAvatar(String objectName);
}
