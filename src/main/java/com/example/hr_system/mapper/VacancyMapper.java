package com.example.hr_system.mapper;

import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Vacancy;

import java.util.List;

public interface VacancyMapper {

    VacancyResponse toDto(Vacancy vacancy);

    List<VacancyResponse> toDtos(List<Vacancy> vacancies);

    VacancyResponse requestToResponse(VacancyRequest vacancyRequest);
}
