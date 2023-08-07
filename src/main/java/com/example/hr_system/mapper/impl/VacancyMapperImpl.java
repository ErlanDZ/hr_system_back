package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.salary.SalaryRequest;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Salary;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.mapper.*;
import com.example.hr_system.repository.EmployerRepository;
import com.example.hr_system.repository.PositionRepository;
import com.example.hr_system.repository.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
//      private TypeOfEmployment typeOfEmployment;
//
//    private Double salary;
//
//    private Valute valute;

    @Override
    public VacancyResponse toDto(Vacancy vacancy) {
        VacancyResponse vacancyResponse = new VacancyResponse();
        //vacancyResponse.setExperience(vacancy.getE);
        vacancyResponse.setId(vacancy.getId());
        vacancyResponse.setAbout_company(vacancy.getAbout_company());
        vacancyResponse.setPosition(vacancy.getPosition()!=null?
                vacancy.getPosition().getName():
                null);
        vacancyResponse.setIndustry(vacancy.getIndustry());
        vacancyResponse.setDescription(vacancy.getDescription());
        vacancyResponse.setSkills(vacancy.getSkills());
        vacancyResponse.setSalaryResponse(salaryMapper.toDto(vacancy.getSalary()));
        vacancyResponse.setTypeOfEmploymentS(TypeOfEmployment.valueOf(vacancy.getTypeOfEmploymentS()));
        vacancyResponse.setExperience(vacancy.getExperience());
        vacancyResponse.setContactInformationResponse(contactInformationMapper.toDto(vacancy.getContactInformation()));
        vacancyResponse.setAdditionalInformation(vacancy.getAdditionalInformation());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        vacancyResponse.setDate(timeStamp);
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
        vacancyResponse.setAbout_company(vacancyRequest.getAbout_company());
        vacancyResponse.setPosition(vacancyRequest.getPosition());
        vacancyResponse.setIndustry(vacancyRequest.getIndustry());
        vacancyResponse.setDescription(vacancyRequest.getDescription());
        vacancyResponse.setSkills(vacancyRequest.getSkills());
        vacancyResponse.setSalaryResponse(salaryMapper.toDto(salaryMapper.toEntity(vacancyRequest.getSalaryRequest())));
        vacancyResponse.setTypeOfEmploymentS(TypeOfEmployment.valueOf(vacancyRequest.getTypeOfEmploymentS()));
        vacancyResponse.setExperience(vacancyRequest.getExperience());
        vacancyResponse.setContactInformationResponse(contactInformationMapper.requestToresponse(vacancyRequest.getContactInformationRequest()));
        vacancyResponse.setAdditionalInformation(vacancyRequest.getAdditionalInformation());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        vacancyResponse.setDate(timeStamp);
//        vacancyResponse.setPositionResponse(vacancyRequest.getPositionRequest());
//        vacancyResponse.setSalaryId(vacancyResponse.getSalaryId());
//        vacancyResponse.setSkills(vacancyRequest.getSkills());
//        vacancyResponse.setContactInformationResponse(contactInformationMapper.requestToresponse(vacancyRequest.getContactInformationRequest()));
//        vacancyResponse.setName(vacancyRequest.getName());
//        vacancyResponse.setDescription(vacancyRequest.getDescription());
        return vacancyResponse;
    }

    @Override
    public Salary toEntity(SalaryRequest salaryRequest) {
        Salary salary = new Salary();
        salary.setSalary(salaryRequest.getSalary());
        salary.setValute(salaryRequest.getValute());
        salary.setSalaryType(salaryRequest.getSalaryType());
        //salary.setVacancy(employerRepository.findById(employerId));
        return null;
    }


}
