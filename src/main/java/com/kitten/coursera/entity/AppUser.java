package com.kitten.coursera.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_usr")
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
//    @Column(name = "c_avatar")
//    private String avatar;
//    @Column(name = "c_date_registration")
//    private Date dateRegistration;
    @Column(name = "c_phone")
    private Integer phone;

}
