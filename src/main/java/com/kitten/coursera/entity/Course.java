package com.kitten.coursera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "c_id")
    private UUID id;
    @Column(name = "c_title")
    public String title;
    @Column(name = "c_description")
    public String description;
    @Column(name = "c_author")
    public String author;
    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    public Course(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }
}
