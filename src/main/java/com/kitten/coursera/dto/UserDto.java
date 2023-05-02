package com.kitten.coursera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String nickname;
    private String password;
    private String fullName;
    private String eMail;
    private Integer phone;
}
