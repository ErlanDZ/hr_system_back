package com.example.hr_system.entities;


import com.example.hr_system.enums.StatusOfVacancy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String about_company;
    private String industry;
    private String description;
    private String skills;
    private String typeOfEmploymentS;
    private String experience;
    private String additionalInformation;
    private Date date;
    @Enumerated(EnumType.STRING)
    private StatusOfVacancy statusOfVacancy;
    @ManyToMany
    private List<JobSeeker> jobSeekers;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.REMOVE})
    private Employer employer;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
    private Position position;
    @OneToOne(cascade = CascadeType.ALL)
    private Salary salary;
    private Integer response;
    private Boolean isResponse = false;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactInformation contactInformation;

    @ManyToMany(mappedBy = "vacancies")
    private List<JobSeeker> jobSeeker;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData resume;
}
