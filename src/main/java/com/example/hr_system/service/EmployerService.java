package com.example.hr_system.service;

import com.example.hr_system.dto.SimpleResponse;
import com.example.hr_system.dto.employer.EmployerRequest;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponse;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.entities.ImageData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface EmployerService {
    boolean selectToFavorites(Long jobSeekerId, Long employerId)throws NotFoundException;

    List<CandidateResponses> favoriteCandidateResponses(Long employerId);

    List<CandidateResponses> getAllCandidates();

    List<EmployerResponses> getAll();


    Response imageToResponse(ImageData image);

    EmployerResponse save(EmployerRequest employerRequest);

    EmployerResponses update(Long id, EmployerRequests employerRequests);

    EmployerResponses getById(Long id);

    SimpleResponse deleteById(Long id);

    Employer convertToEntity(Long id, EmployerRequests employerRequests);


    ImageData responseToImage(Response image);

    Response uploadImage(MultipartFile file, Long id) throws IOException;
}
