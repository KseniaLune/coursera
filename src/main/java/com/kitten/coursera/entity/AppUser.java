package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_usr")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
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

}
