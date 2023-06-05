package com.kitten.coursera.repo;

import com.kitten.coursera.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface LessonRepo extends JpaRepository<Lesson, UUID> {
}
