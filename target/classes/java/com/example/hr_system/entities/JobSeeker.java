package com.example.hr_system.entities;


import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.Month;
import com.example.hr_system.enums.Role;
import com.example.hr_system.enums.StatusOfJobSeeker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job_seeker_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private StatusOfJobSeeker statusOfJobSeeker;
    // Add this field to your JobSeeker entity
    private LocalDateTime userApplicationDate;


    private String phoneNumber;

    @Column(name = "about")
    private String about;

    private Education education;

    private String institution;
    private Month month;
    private LocalDate year;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private Position position;
    private String working_place;
    private String firstname;
    private String lastname;

    private String email;
    private String password;

    private LocalDate graduationDate;

    private boolean untilNow;
    private String skills;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData resume;

    @Enumerated(EnumType.STRING)
    @Column(name="rol")
    private Role role;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @ManyToMany(mappedBy = "favorites")
    private List<Employer> employers;

    private Long isFavorite;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH})
    private Experience experience;

    @OneToMany(mappedBy = "userId")
    private List<Notification> notification;

}
