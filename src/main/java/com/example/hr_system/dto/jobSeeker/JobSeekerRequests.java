package com.example.hr_system.dto.jobSeeker;


import com.example.hr_system.dto.image.Response;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.Month;
import com.example.hr_system.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class JobSeekerRequests {

    private Response image;
    private String firstname;
    private String lastname;
    private LocalDate birthday;

    private String country;
    private String city;
    private String address;
    private String phoneNumber;
    private String email;
    @Column(name = "about")
    private String about;
    private Education education;
    private String institution;
    private Month month;
    private LocalDate year;
    private String position;
    private String working_place;
    private String opyt;


    private LocalDate graduationDate;

    private boolean untilNow;
    private String skills;


    private String resume;
    private Role role;


}
