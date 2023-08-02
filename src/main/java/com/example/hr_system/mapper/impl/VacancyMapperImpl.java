package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.salary.SalaryRequest;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Salary;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.mapper.*;
import com.example.hr_system.repository.EmployerRepository;
import com.example.hr_system.repository.PositionRepository;
import com.example.hr_system.repository.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class VacancyMapperImpl implements VacancyMapper {
    private PositionMapper positionMapper;
    private SalaryMapper salaryMapper;
    private ContactInformationMapper contactInformationMapper;
    private final PositionRepository positionRepository;
    private final EmployerRepository employerRepository;
    private final VacancyRepository vacancyRepository;

    @Override
    public VacancyResponse toDto(Vacancy vacancy) {
        VacancyResponse vacancyResponse = new VacancyResponse();
        //vacancyResponse.setExperience(vacancy.getE);
        vacancyResponse.setId(vacancy.getId());
        vacancyResponse.setName(vacancy.getName());
        vacancyResponse.setSkills(vacancy.getSkills());
        vacancyResponse.setDescription(vacancy.getDescription());
        vacancyResponse.setStatusOfVacancy(String.valueOf(vacancy.getStatusOfVacancy()));

        if (vacancy.getPosition() != null) {
            vacancyResponse.setPositionResponse(vacancy.getPosition().getName());
        }
        if (vacancy.getSalary() != null) {
            vacancyResponse.setSalaryId(vacancy.getSalary().getId());
        }
        if (vacancy.getContactInformation() != null) {
            vacancyResponse.setContactInformationResponse(contactInformationMapper.toDto(vacancy.getContactInformation()));
        }
        return vacancyResponse;
    }

    @Override
    public List<VacancyResponse> toDtos(List<Vacancy> vacancies) {
        List<VacancyResponse> vacancyResponses = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            vacancyResponses.add(toDto(vacancy));
        }

        return vacancyResponses;
    }

    @Override
    public VacancyResponse requestToResponse(VacancyRequest vacancyRequest) {
        VacancyResponse vacancyResponse = new VacancyResponse();
        vacancyResponse.setPositionResponse(vacancyRequest.getPositionRequest());
        vacancyResponse.setSalaryId(vacancyResponse.getSalaryId());
        vacancyResponse.setSkills(vacancyRequest.getSkills());
        vacancyResponse.setContactInformationResponse(contactInformationMapper.requestToresponse(vacancyRequest.getContactInformationRequest()));
        vacancyResponse.setName(vacancyRequest.getName());
        vacancyResponse.setDescription(vacancyRequest.getDescription());
        return vacancyResponse;
    }

    @Override
    public Salary toEntity(SalaryRequest salaryRequest) {
        Salary salary = new Salary();
        salary.setSalary(salaryRequest.getSalary());
        salary.setValute(salaryRequest.getValute());
        salary.setTypeOfEmployment(salaryRequest.getTypeOfEmployment());
        //salary.setVacancy(employerRepository.findById(employerId));
        return null;
    }


}
