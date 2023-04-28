package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_title")
    public String title;
    @Column(name = "c_description")
    public String description;
    @Column(name = "c_author")
    public String author;

    public Course(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }
}
