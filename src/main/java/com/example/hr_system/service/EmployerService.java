package com.example.hr_system.service;

import com.example.hr_system.dto.SimpleResponse;
import com.example.hr_system.dto.employer.EmployerRequest;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponse;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.file.FileResponse;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.entities.Employer;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface EmployerService {
    boolean selectToFavorites(Long jobSeekerId, Long employerId)throws NotFoundException;

    List<CandidateResponses> favoriteCandidateResponses(Employer employer);

    List<CandidateResponses> getAllCandidates(Long employerId);

    List<EmployerResponses> getAll();



    EmployerResponse save(EmployerRequest employerRequest);

    EmployerResponses update(Long id, EmployerRequests employerRequests);

    EmployerResponses getById(Long id);

    SimpleResponse deleteById(Long id);

    Employer convertToEntity(Long id, EmployerRequests employerRequests);






    List<CandidateResponses> filter(String position, String education, String country, String city, String experience);

    FileResponse uploadResume(MultipartFile file, Long id) throws IOException;


    // FileResponse uploadResume(MultipartFile file, Long id) throws IOException;

    // FileResponse uploadResume(MultipartFile file, Long id);
}
