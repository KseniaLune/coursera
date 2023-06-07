package com.kitten.coursera.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

//    private UUID id;
    private String nickname;
    private String password;
    private String fullName;
    private String eMail;
    private Integer phone;


}
