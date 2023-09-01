package com.kitten.coursera.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "courses")
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseElastic {
    private UUID id;
    private String title;
    private String description;
}
