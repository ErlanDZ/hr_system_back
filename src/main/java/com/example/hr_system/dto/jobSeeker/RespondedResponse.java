package com.example.hr_system.dto.jobSeeker;

import com.example.hr_system.enums.StatusOfJobSeeker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondedResponse {

    private Long id;
    private Integer respondedCount;
    private String firstname;
    private String lastname;
    private String position;
    private String category;
    private String experience;
    private String country;
    private String city;
    private LocalDate localDate;
    private StatusOfJobSeeker statusOfJobSeeker;
}