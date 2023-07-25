package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.mapper.*;
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

    @Override
    public VacancyResponse toDto(Vacancy vacancy) {
        VacancyResponse vacancyResponse = new VacancyResponse();
        vacancyResponse.setId(vacancy.getId());
        vacancyResponse.setName(vacancy.getName());
        vacancyResponse.setSkills(vacancy.getSkills());
        vacancyResponse.setDescription(vacancy.getDescription());
        vacancyResponse.setContactInfo(vacancy.getContactInfo());

        if (vacancy.getPosition() != null) {
            vacancyResponse.setPositionResponse(positionMapper.toDto(vacancy.getPosition()));
        }
        if (vacancy.getSalary() != null) {
            vacancyResponse.setSalaryResponse(salaryMapper.toDto(vacancy.getSalary()));
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
        vacancyResponse.setPositionResponse(positionMapper.requestToResponse(vacancyRequest.getPositionRequest()));
        vacancyResponse.setSalaryResponse(vacancyResponse.getSalaryResponse());
        vacancyResponse.setSkills(vacancyRequest.getSkills());
        vacancyResponse.setContactInformationResponse(contactInformationMapper.requestToresponse(vacancyRequest.getContactInformationRequest()));
        vacancyResponse.setName(vacancyRequest.getName());
        vacancyResponse.setDescription(vacancyRequest.getDescription());
        vacancyResponse.setContactInfo(vacancyRequest.getContactInfo());
        return vacancyResponse;
    }


}
