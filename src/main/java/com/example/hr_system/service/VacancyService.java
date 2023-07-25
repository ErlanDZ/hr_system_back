package com.example.hr_system.service;

import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.entities.Vacancy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface VacancyService {
    VacancyResponse saveVacancy(Long id,VacancyRequest vacancyRequest);


    void delete(Long id);



    List<Vacancy> getAll();

    String getIdFromSecurity();

    VacancyResponse update(Long id, VacancyRequest vacancyRequest);

    List<JobSeekerVacanciesResponses> jobSeekerVacancies();
//    List<JobSeekerVacanciesResponses> findByVacancyJobSeekerVacancies(Long id);

    List<VacancyResponse> getMyVacancies(Long id);


//
//    List<Vacancy> employeeVacancies();

    List<JobSeekerVacanciesResponses> searchVacancy(String search);

    List<JobSeekerVacanciesResponses> filter(String category, String position, String country, String city, Experience experience);

    VacancyResponse updateById(Long id, VacancyRequest vacancyRequest);

    VacancyResponse updateEmployerVacancyByIds(Long employerId, Long vacancyId, VacancyRequest vacancyRequest);

    Response uploadImage(MultipartFile file, Long id) throws IOException;
}

