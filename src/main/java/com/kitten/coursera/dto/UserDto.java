package com.kitten.coursera.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

//    private UUID id;
    private String nickname;
    @JsonIgnore
    private String password;
    private String fullName;
    private String eMail;
    private Integer phone;


}
