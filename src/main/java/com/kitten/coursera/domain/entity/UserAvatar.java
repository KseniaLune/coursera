package com.kitten.coursera.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserAvatar {
    private MultipartFile file;
}
