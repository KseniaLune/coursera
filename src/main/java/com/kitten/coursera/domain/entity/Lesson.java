package com.kitten.coursera.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_lesson")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "c_id")
    private UUID id;
    @Column(name = "c_title")
    private String title;
    @Column(name = "c_text", columnDefinition = "TEXT")
    private String text;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "c_course_id", nullable = false)
    private Course course;
    @Column(name = "c_file")
    @CollectionTable(name = "t_lesson_file")
    @ElementCollection
    private List<String> file;
}
