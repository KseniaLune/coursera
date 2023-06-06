package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Lob
    @Column(name = "c_text")
    private String text;
    @ManyToOne(optional = false)
    private Course course;
}
