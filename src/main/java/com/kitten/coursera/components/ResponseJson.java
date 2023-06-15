package com.kitten.coursera.components;

import com.kitten.coursera.exeption.ExBody;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJson {
    private String result;
    private ExBody exceptions;
}
