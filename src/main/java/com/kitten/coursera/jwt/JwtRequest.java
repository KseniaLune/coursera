package com.kitten.coursera.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {
    @NotNull(message = "E-mail must be not null")
    private String eMail;

    @NotNull(message = "password must be not null")
    private String password;
}
