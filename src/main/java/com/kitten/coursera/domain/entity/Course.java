package com.kitten.coursera.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;



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
    private List<Lesson> lessons = new ArrayList<>();

    public Course(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public void addLessonToCourse(Lesson lesson){
        lessons.add(lesson);
        lesson.setCourse(this);
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", author='" + author + '\'' +
            '}';
    }

    public Course(UUID id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }
}
