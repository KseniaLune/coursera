package com.kitten.coursera.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "t_user_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "c_id")
    private UUID id;
    @Column(name = "c_usr_id")
    private UUID userId;
    @Column(name = "c_course_id ")
    private UUID courseId;
}
