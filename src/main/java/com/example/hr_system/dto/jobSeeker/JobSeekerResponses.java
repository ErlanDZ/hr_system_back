package com.example.hr_system.dto.jobSeeker;

import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.Month;
import com.example.hr_system.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerResponses {
    private Long id;



    private Long imageId;
    private String firstname;
    private String lastname;
    private String about;
    private Education education;
    private String institution;
    private Month month;
    private LocalDate year;
    private String position;
    private String working_place;
    private Long resumeId;
    private LocalDate birthday;
    private String country;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private Role role;
    private Integer responses;




}
