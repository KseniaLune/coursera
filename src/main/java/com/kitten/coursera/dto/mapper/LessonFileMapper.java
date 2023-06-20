package com.kitten.coursera.dto.mapper;

import com.kitten.coursera.domain.entity.LessonFile;
import com.kitten.coursera.dto.LessonFileDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonFileMapper implements Mappable<LessonFile, LessonFileDto>{
    @Override
    public LessonFileDto toDto(LessonFile entity) {
        return LessonFileDto.builder()
            .file(entity.getFile())
            .build();
    }

    @Override
    public List<LessonFileDto> toDto(List<LessonFile> entities) {
        return entities.stream()
            .map(e -> LessonFileDto.builder()
                .file(e.getFile())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public LessonFile toEntity(LessonFileDto dto) {
        LessonFile lessonFile = LessonFile.builder()
            .file(dto.getFile())
            .build();
        return lessonFile;
    }

    @Override
    public List<LessonFile> toEntity(List<LessonFileDto> dtos) {
        return dtos.stream()
            .map(d -> LessonFile.builder()
                .file(d.getFile())
                .build())
            .collect(Collectors.toList());
    }
}
