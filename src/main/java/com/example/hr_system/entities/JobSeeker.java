package com.example.hr_system.entities;


import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.Month;
import com.example.hr_system.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "job_seeker_table")
@Data
public class JobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "jobSeeker")
    private User user;



    private LocalDate birthday;

    private String country;
    private String city;
    private String address;
    private String phoneNumber;

    @Column(name = "about")
    private String about;

    private Education education;

    private String institution;
    private Month month;
    private LocalDate year;
    private String position;
    private String working_place;
    private String firstname;
    private String lastname;
    @OneToOne(cascade = CascadeType.ALL)
    private ImageData image;
    private String email;
    private String password;

    private LocalDate graduationDate;

    private boolean untilNow;
    private String skills;


    private String resume;
    @Enumerated(EnumType.STRING)
    @Column(name="rol")
    private Role role;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @ManyToMany(mappedBy = "favorites")
    private List<Employer> employers;

    private Long isFavorite;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST,CascadeType.REFRESH})
    private Experience experience;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobSeeker")
//    private List<Resume> resumeList;

//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobSeeker")
//    private List<Resume> resumeList;



}
