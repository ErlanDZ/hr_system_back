package com.example.hr_system.dto.responded;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespondedJobSeekersResponse {
    private String firstname;
    private String lastname;
    private String position;
    private String industry;
    private String experience;
    private String country_city;
}
