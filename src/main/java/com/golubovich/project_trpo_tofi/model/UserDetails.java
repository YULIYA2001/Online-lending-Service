package com.golubovich.project_trpo_tofi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "UserDetails")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(length = 50)
    private String patronymic;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate = new Date();

    @Column(nullable = false, unique = true, length = 15)
    private String passportID;

    @OneToOne(mappedBy = "userDetails")
    private User user;
}
