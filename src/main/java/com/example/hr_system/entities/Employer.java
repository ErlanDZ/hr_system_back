package com.example.hr_system.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String companyName;

    private String email;

    private String password;

    private String address;

    private String phoneNumber;

    private String city;

    private String aboutCompany;

    private String country;



    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employer")
    private User user;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer")
    private List<Vacancy> vacancyList;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private List<JobSeeker> favorites;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageData image;

    public void addVacancy(Vacancy vacancy) {
        if (vacancyList == null) {
            vacancyList = new ArrayList<>();
        }
        vacancyList.add(vacancy);
    }
//sekflm





}

