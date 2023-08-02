package com.example.hr_system.dto.vacancy;

import com.example.hr_system.dto.contactInformation.ContactInformationResponse;
import com.example.hr_system.dto.position.PositionResponse;
import com.example.hr_system.dto.salary.SalaryRequest;
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
    private String statusOfVacancy;
    private SalaryResponse salaryResponse;
    private String experience;
    private String positionResponse;
    private Long salaryId;
    private ContactInformationResponse contactInformationResponse;




}
