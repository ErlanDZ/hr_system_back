package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequest;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.dto.jobSeeker.RespondedResponse;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.mapper.FileMapper;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.mapper.VacancyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JobSeekerMapperImpl implements JobSeekerMapper {
    private VacancyMapper vacancyMapper;
    private final FileMapper fileMapper;

    @Override
    public JobSeekerResponses toDto(JobSeeker jobSeeker) {
        if (jobSeeker == null) {
            return null;
        }
        JobSeekerResponses response = new JobSeekerResponses();
        response.setId(jobSeeker.getId());

        if (jobSeeker.getResume() != null) {
            response.setImageId(jobSeeker.getResume().getId());
        }
        response.setFirstname(jobSeeker.getFirstname());
        response.setLastname(jobSeeker.getLastname());
        response.setAbout(jobSeeker.getAbout());
        response.setEducation(jobSeeker.getEducation());
        response.setInstitution(jobSeeker.getInstitution());
        response.setMonth(jobSeeker.getMonth());
        response.setYear(jobSeeker.getYear());
        response.setPosition(
                jobSeeker.getPosition()==null?null:
                jobSeeker.getPosition().getName());
        response.setWorking_place(jobSeeker.getWorking_place());
        response.setResumeId(
                jobSeeker.getResume()==null?null:
                jobSeeker.getResume().getId());
        response.setBirthday(jobSeeker.getBirthday());
        response.setCountry(jobSeeker.getCountry());
        response.setCity(jobSeeker.getCity());
        response.setAddress(jobSeeker.getAddress());
        response.setEmail(jobSeeker.getEmail());
        response.setPhoneNumber(jobSeeker.getPhoneNumber());
        response.setRole(jobSeeker.getRole());
        return response;
    }

    @Override
    public List<JobSeekerResponses> toDtos(List<JobSeeker> jobSeekers) {
        List<JobSeekerResponses> jobSeekerResponses = new ArrayList<>();
        for (JobSeeker jobSeeker : jobSeekers) {
            jobSeekerResponses.add(toDto(jobSeeker));
        }
        return jobSeekerResponses;
    }

    @Override
    public JobSeekerVacanciesResponses convertToVacancyJobSeekerResponse(Vacancy vacancy) {
        if (vacancy == null) {
            return null;
        }
        JobSeekerVacanciesResponses vacanciesResponses = new JobSeekerVacanciesResponses();
        vacanciesResponses.setId(vacancy.getId());
        vacanciesResponses.setOwnerName(vacancy.getAbout_company());
        vacanciesResponses.setVacancyResponse(vacancyMapper.toDto(vacancy));
        return null;
    }

    @Override
    public List<JobSeekerVacanciesResponses> convertToVacancyJobSeekerResponses(List<Vacancy> vacancies) {
        List<JobSeekerVacanciesResponses> jobSeekerVacanciesResponses = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            jobSeekerVacanciesResponses.add(convertToVacancyJobSeekerResponse(vacancy));
        }
        return jobSeekerVacanciesResponses;
    }

    @Override
    public List<CandidateResponses> listConvertToCandidateResponse(List<JobSeeker> jobSeekers) {
        List<CandidateResponses> candidateResponses = new ArrayList<>();
        for(JobSeeker jobSeeker: jobSeekers){
            candidateResponses.add(convertToCandidateResponse(jobSeeker));
        }
        return candidateResponses;
    }
    @Override
    public CandidateResponses convertToCandidateResponse(JobSeeker jobSeeker) {
        CandidateResponses candidateResponses = new CandidateResponses();
        if (jobSeeker.getResume()==(null)){

        }else {
            candidateResponses.setImageId(jobSeeker.getResume().getId());

        }
        candidateResponses.setFirstname(jobSeeker.getFirstname());
        candidateResponses.setLastname(jobSeeker.getLastname());
        candidateResponses.setPosition(jobSeeker.getPosition()==null?null:
                jobSeeker.getPosition().getName());
        candidateResponses.setExperience(jobSeeker.getExperience()==null?null:
                jobSeeker.getExperience().getName());
        candidateResponses.setCity(jobSeeker.getCity());
        candidateResponses.setCountry(jobSeeker.getCountry());
        return candidateResponses;
    }

    @Override
    public List<RespondedResponse> toDtosForListResponded(List<JobSeeker> jobSeekers) {
        List<RespondedResponse> respondedResponses = new ArrayList<>();
        for (JobSeeker jobSeeker: jobSeekers){
            respondedResponses.add(toDtoForResponded(jobSeeker));
        }
        return respondedResponses;
    }

    @Override
    public RespondedResponse toDtoForResponded(JobSeeker jobSeeker) {
        if (jobSeeker == null) {
            System.out.println("its null\n\n\n");
            return null;
        }
        RespondedResponse respondedResponses = new RespondedResponse();
        respondedResponses.setId(jobSeeker.getId());
        respondedResponses.setFirstname(jobSeeker.getFirstname());
        respondedResponses.setLastname(jobSeeker.getLastname());
        respondedResponses.setPosition(jobSeeker.getPosition()==null?null:
                jobSeeker.getPosition()
                .getName());
        if (jobSeeker.getPosition()!=null){
            respondedResponses.setCategory(jobSeeker.getPosition().getCategory()==null?null:
                    jobSeeker.getPosition().getCategory().getName());
        }

        respondedResponses.setExperience(jobSeeker.getExperience()==null?null:
                jobSeeker.getExperience().getName());
        respondedResponses.setCountry(jobSeeker.getCountry());
        respondedResponses.setCity(jobSeeker.getCity());
        respondedResponses.setStatusOfJobSeeker(jobSeeker.getStatusOfJobSeeker());

        return respondedResponses;
    }

    @Override
    public JobSeeker toEntity(JobSeekerRequest jobSeekerRequest) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeekerRequest.setFirstname(jobSeekerRequest.getFirstname());
        jobSeekerRequest.setLastname(jobSeekerRequest.getLastname());
        jobSeekerRequest.setEmail(jobSeekerRequest.getEmail());
        jobSeekerRequest.setPassword(jobSeekerRequest.getPassword());
        return jobSeeker;
    }
}
