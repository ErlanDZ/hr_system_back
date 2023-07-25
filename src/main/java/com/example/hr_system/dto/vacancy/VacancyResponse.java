package com.example.hr_system.dto.vacancy;

import com.example.hr_system.dto.contactInformation.ContactInformationResponse;
import com.example.hr_system.dto.position.PositionResponse;
import com.example.hr_system.dto.salary.SalaryResponse;
import com.example.hr_system.entities.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacancyResponse {
    private Long id;
    private String name;
    private String description;
    private String skills;
    private String contactInfo;
    private PositionResponse positionResponse;
    private SalaryResponse salaryResponse;
    private ContactInformationResponse contactInformationResponse;




}
