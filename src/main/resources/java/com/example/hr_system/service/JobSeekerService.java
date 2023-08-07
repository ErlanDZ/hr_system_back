package com.example.hr_system.service;

import com.example.hr_system.dto.file.FileResponse;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.*;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Position;
import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.StatusOfJobSeeker;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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

   // List<RespondedResponse> filterJobSeekers(StatusOfJobSeeker statusOfJobSeeker, Experience experience, LocalDate applicationDate);
}
