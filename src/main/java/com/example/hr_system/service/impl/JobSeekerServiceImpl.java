package com.example.hr_system.service.impl;

import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequest;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequests;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponse;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.repository.JobSeekerRepository;
import com.example.hr_system.repository.StorageRepository;
import com.example.hr_system.repository.VacancyRepository;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.StorageService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {
    private final StorageRepository storageRepository;
    private final EmployerService employerService;
    private final JobSeekerMapper jobSeekerMapper;
    private final JobSeekerRepository jobSeekerRepository;
    private final VacancyRepository vacancyRepository;
    private final StorageService storageService;


    @Override
    public List<JobSeeker> getAll() {
        return jobSeekerRepository.findAll();
    }

    @Override
    public List<JobSeekerResponses> getAllJobSeekers() {

        return jobSeekerMapper.toDtos(jobSeekerRepository.findAll());

    }


    @Override
    public JobSeekerResponse save(JobSeekerRequest jobSeeker) {
        JobSeeker jobSeeker1 = new JobSeeker();

        jobSeeker1.setEmail(jobSeeker.getEmail());
        jobSeeker1.setPassword(jobSeeker.getPassword());
        jobSeeker1.setRole(jobSeeker.getRole());
        jobSeekerRepository.save(jobSeeker1);

        return new JobSeekerResponse(jobSeeker1.getId(), jobSeeker1.getFirstname(), jobSeeker1.getLastname(), jobSeeker1.getRole());
    }


    public JobSeekerResponses updateWithImage(Long id, JobSeekerRequests jobSeeker, ImageData imageData) {


        JobSeeker jobSeeker1 = jobSeekerRepository.findById(id).orElseThrow(() -> new RuntimeException("user can be null"));
        imageData.setJobSeeker(jobSeeker1);
        jobSeeker1.setImage(imageData);
        storageRepository.save(imageData);


        jobSeeker1.setImage(imageData);
        jobSeeker1.setFirstname(jobSeeker.getFirstname());
        jobSeeker1.setLastname(jobSeeker.getLastname());
        jobSeeker1.setBirthday(jobSeeker.getBirthday());
        jobSeeker1.setCountry(jobSeeker.getCountry());
        jobSeeker1.setCity(jobSeeker.getCity());
        jobSeeker1.setAddress(jobSeeker.getAddress());
        jobSeeker1.setPhoneNumber(jobSeeker.getPhoneNumber());
        jobSeeker1.setAbout(jobSeeker.getAbout());
        jobSeeker1.setEducation(jobSeeker.getEducation());
        jobSeeker1.setInstitution(jobSeeker.getInstitution());
        jobSeeker1.setMonth(jobSeeker.getMonth());
        jobSeeker1.setYear(jobSeeker.getYear());
        jobSeeker1.setUntilNow(jobSeeker.isUntilNow());
        jobSeeker1.setPosition(jobSeeker.getPosition());
        jobSeeker1.setWorking_place(jobSeeker.getWorking_place());
        jobSeeker1.setSkills(jobSeeker.getSkills());
        jobSeeker1.setResume(jobSeeker.getResume());
        jobSeeker1.setRole(jobSeeker.getRole());

        jobSeekerRepository.save(jobSeeker1);

        return new JobSeekerResponses(jobSeeker1.getId(),
                employerService.imageToResponse(jobSeeker1.getImage()),
                jobSeeker1.getFirstname(),
                jobSeeker1.getLastname(),
                jobSeeker1.getAbout(),
                jobSeeker1.getEducation(),
                jobSeeker1.getInstitution(),
                jobSeeker1.getMonth(),
                jobSeeker1.getYear(),
                jobSeeker1.getPosition(),
                jobSeeker1.getWorking_place(),
                jobSeeker1.getResume(), jobSeeker1.getBirthday(), jobSeeker1.getCountry(),
                jobSeeker1.getCity(),
                jobSeeker1.getAddress(), jobSeeker1.getEmail(), jobSeeker1.getPhoneNumber(), jobSeeker1.getRole());
    }


    @Override
    public JobSeekerResponses update(Long id, JobSeekerRequests jobSeeker) {


        JobSeeker jobSeeker1 = jobSeekerRepository.findById(id).orElseThrow(() -> new RuntimeException("user can be null"));

        jobSeeker1.setImage(employerService.responseToImage(jobSeeker.getImage()));
        jobSeeker1.setFirstname(jobSeeker.getFirstname());
        jobSeeker1.setLastname(jobSeeker.getLastname());
        jobSeeker1.setBirthday(jobSeeker.getBirthday());
        jobSeeker1.setCountry(jobSeeker.getCountry());
        jobSeeker1.setCity(jobSeeker.getCity());
        jobSeeker1.setAddress(jobSeeker.getAddress());
        jobSeeker1.setPhoneNumber(jobSeeker.getPhoneNumber());
        jobSeeker1.setAbout(jobSeeker.getAbout());
        jobSeeker1.setEducation(jobSeeker.getEducation());
        jobSeeker1.setInstitution(jobSeeker.getInstitution());
        jobSeeker1.setMonth(jobSeeker.getMonth());
        jobSeeker1.setYear(jobSeeker.getYear());
        jobSeeker1.setUntilNow(jobSeeker.isUntilNow());
        jobSeeker1.setPosition(jobSeeker.getPosition());
        jobSeeker1.setWorking_place(jobSeeker.getWorking_place());
        jobSeeker1.setSkills(jobSeeker.getSkills());
        jobSeeker1.setResume(jobSeeker.getResume());
        jobSeeker1.setRole(jobSeeker.getRole());

        jobSeekerRepository.save(jobSeeker1);

        return new JobSeekerResponses(jobSeeker1.getId(),
                employerService.imageToResponse(
                        jobSeeker1.getImage()),
                jobSeeker1.getFirstname(),
                jobSeeker1.getLastname(),
                jobSeeker1.getAbout(),
                jobSeeker1.getEducation(),
                jobSeeker1.getInstitution(),
                jobSeeker1.getMonth(),
                jobSeeker1.getYear(),
                jobSeeker1.getPosition(),
                jobSeeker1.getWorking_place(),
                jobSeeker1.getResume(), jobSeeker1.getBirthday(), jobSeeker1.getCountry(),
                jobSeeker1.getCity(),
                jobSeeker1.getAddress(), jobSeeker1.getEmail(), jobSeeker1.getPhoneNumber(), jobSeeker1.getRole());
    }

    @Override
    public SimpleMessage delete(Long id) {
        jobSeekerRepository.deleteById(id);
        return new SimpleMessage("You have deleted" + id);
    }

    @Override
    public JobSeeker getById(Long id) {
        return jobSeekerRepository.findById(id).orElseThrow(() -> new NotFoundException("JobSeeker not found!"));
    }

    @Override
    public void responseForVacancy(Long vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found!"));
        if (vacancy.getIsResponse().equals(false)) {
            vacancy.setResponse(vacancy.getJobSeekers().size() + 1);
        }
        vacancy.setIsResponse(true);
        vacancyRepository.save(vacancy);
    }

    @Override
    public Response uploadImage(MultipartFile file, Long id) throws IOException {
        JobSeeker jobSeeker = getById(id);
        if (jobSeeker.getImage() != null) {
            ImageData image = jobSeeker.getImage();
            jobSeeker.setImage(null);
            ImageData save = storageService.uploadImage(file, image);
            jobSeeker.setImage(save);
            jobSeekerRepository.save(jobSeeker);
        } else {
            ImageData image = storageService.uploadImage(file);
            jobSeeker.setImage(image);
            jobSeekerRepository.save(jobSeeker);
        }

        return null;
    }
}
