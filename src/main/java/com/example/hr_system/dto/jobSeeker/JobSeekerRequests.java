package com.example.hr_system.dto.jobSeeker;


import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.Month;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerRequests {

    private Long imageId;
    private String firstname;
    private String lastname;
    private LocalDate birthday;

    private String country;
    private String city;
    private String address;
    private String phoneNumber;
    private String about;
    private Education education;
    private String institution;
    private Month month;
    private LocalDate year;
    private String position;
    private String experience;
    private String working_place;

    private LocalDate graduationDate;

    private boolean untilNow;
    private String skills;


    private Long resumeId;


}
