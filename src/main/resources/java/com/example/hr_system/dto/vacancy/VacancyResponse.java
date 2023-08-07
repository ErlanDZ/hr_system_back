package com.example.hr_system.dto.vacancy;

import com.example.hr_system.dto.contactInformation.ContactInformationRequest;
import com.example.hr_system.dto.contactInformation.ContactInformationResponse;
import com.example.hr_system.dto.position.PositionResponse;
import com.example.hr_system.dto.salary.SalaryRequest;
import com.example.hr_system.dto.salary.SalaryResponse;
import com.example.hr_system.entities.ContactInformation;
import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.enums.Valute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacancyResponse {
    private Long id;
    private String about_company;
    private String position;
    private String industry;
    private String description;
    private String skills;
    private SalaryResponse salaryResponse;
    private String typeOfEmploymentS;
    private String experience;
    private ContactInformationResponse contactInformationResponse;
    private String additionalInformation;
    private String date;





}
