package com.example.hr_system.mapper;

import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.jobSeeker.*;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Vacancy;

import java.util.List;

public interface JobSeekerMapper {
    JobSeekerResponses toDto(JobSeeker jobSeeker);

    List<JobSeekerResponses> toDtos(List<JobSeeker>jobSeekers);

    JobSeekerVacanciesResponses convertToVacancyJobSeekerResponse(Vacancy vacancy);

    List<JobSeekerVacanciesResponses> convertToVacancyJobSeekerResponses(List<Vacancy> vacancies);

    List<CandidateResponses> listConvertToCandidateResponse(List<JobSeeker> jobSeekers);

    CandidateResponses convertToCandidateResponse(JobSeeker jobSeeker);

    List<RespondedResponse> toDtosForListResponded(List<JobSeeker> jobSeekers);

    RespondedResponse toDtoForResponded(JobSeeker jobSeeker);

    JobSeeker toEntity(JobSeekerRequest jobSeekerRequest);

    //  ImageData responseToImage(Response image);
}
