package com.example.hr_system.service;

import com.example.hr_system.dto.file.FileResponse;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequest;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequests;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponse;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Position;
import com.example.hr_system.enums.Education;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface JobSeekerService {

    FileResponse uploadResume(MultipartFile file, Long id) throws IOException;

    List<JobSeeker> getAll();

    List<JobSeekerResponses> getAllJobSeekers();

    JobSeekerResponse save(JobSeekerRequest jobSeeker);
    JobSeekerResponses update(Long id, JobSeekerRequests jobSeeker);
    SimpleMessage delete(Long id);
    JobSeeker getById(Long id);
    void responseForVacancy(Long vacancyId);


    List<JobSeeker> filterJobSeekers(
            Position position,
            Education education,
            String country,
            String city,
            Experience experience
    );
    List<JobSeeker> searchByFirstAndLastName(
            String firstname,
            String lastname
    );

}
