package com.kitten.coursera.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "nickname has to be filled")
    @NotNull(message = "nickname has to be filled")
    private String nickname;
    @NotBlank(message = "password has to be filled")
    @NotNull(message = "password has to be filled")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank(message = "fullName has to be filled")
    @NotNull(message = "fullName has to be filled")
    private String fullName;
    @NotBlank(message = "eMail has to be filled")
    @NotNull(message = "eMail has to be filled")
    private String eMail;
    @NotBlank(message = "phone has to be filled")
    @NotNull(message = "phone has to be filled")
    private Integer phone;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String avatar;

}
