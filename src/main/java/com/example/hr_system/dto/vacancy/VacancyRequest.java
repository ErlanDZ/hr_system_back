package com.example.hr_system.dto.vacancy;

import com.example.hr_system.dto.contactInformation.ContactInformationRequest;
import com.example.hr_system.dto.contactInformation.ContactInformationResponse;
import com.example.hr_system.dto.position.PositionRequest;
import com.example.hr_system.dto.salary.SalaryRequest;
import com.example.hr_system.entities.ContactInformation;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRequest {
    private String name;
    private String description;
    private String skills;
    private String positionRequest;
    private SalaryRequest salaryRequest;
    private String experience;
    private Date date;
    private ContactInformationRequest contactInformationRequest;
    private String additionalInformation;
}
