package com.kitten.coursera.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

//    private UUID id;
    private String nickname;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String fullName;
    private String eMail;
    private Integer phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> avatar;

}
