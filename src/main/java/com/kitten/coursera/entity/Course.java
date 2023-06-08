package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

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

//    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<Lesson> lessons = new ArrayList<>();

    public Course(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

//    public void addLesson(Lesson lesson){
//        lesson.setCourse(this);
//        this.lessons.add(lesson);
//    }
}
