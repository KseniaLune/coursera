package com.kitten.coursera.components;

import com.kitten.coursera.domain.exception.ExBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJson {
    private String result;
    private Exception e;
}
