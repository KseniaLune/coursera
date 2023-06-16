package com.kitten.coursera.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "t_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "c_id")
    private UUID id;

    @Column(name = "c_role")
    @Enumerated(EnumType.STRING)
    private RoleName role;

    public enum RoleName{
        ROLE_OWNER, ROLE_ADMIN, ROLE_PROFESSOR, ROLE_STUDENT
    }
}
