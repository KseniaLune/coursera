package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Entity
@Table(name = "t_usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "c_id")
    private UUID id;
    @Column(name = "c_nickname")
    private String nickname;
    @Column(name = "c_password")
    private String password;
    @Column(name = "c_full_name")
    private String fullName;
    @Column(name = "c_e_mail")
    private String eMail;
    @Column(name = "c_phone")
    private Integer phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",
        joinColumns = @JoinColumn(name = "c_user_id", referencedColumnName = "c_id"),
        inverseJoinColumns = @JoinColumn(name = "c_role_id", referencedColumnName = "c_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    public void addRolesToUser(Role role){
        roles.add(role);
    }
}
