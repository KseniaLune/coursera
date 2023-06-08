package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table(name = "t_lesson")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Lesson {
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
}
