package com.example.hr_system.service.impl;

import com.example.hr_system.dto.file.FileResponse;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequest;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequests;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponse;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.*;
import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.Role;
import com.example.hr_system.mapper.FileMapper;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.repository.*;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.FileDataService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.StorageService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
@AllArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {
    private final StorageRepository storageRepository;
    private final JobSeekerMapper jobSeekerMapper;
    private final JobSeekerRepository jobSeekerRepository;
    private final VacancyRepository vacancyRepository;
    private final StorageService storageService;
    private final PositionRepository positionRepository;
    private final FileDataService fileDataService;
    private final UserRepository userRepository;
    private final FileMapper fileMapper;

    @Override
    public FileResponse uploadResume(MultipartFile file, Long id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("user not found!" + id));
        JobSeeker jobSeeker = user.getJobSeeker();

            if (jobSeeker.getResume() != null) {
                FileData fileData = jobSeeker.getResume();
                jobSeeker.setResume(fileData);
                FileData save = fileDataService.uploadFile(file, fileData);
                jobSeeker.setResume(save);
                jobSeekerRepository.save(jobSeeker);
               return fileMapper.toDto(save);
            } else {
                FileData fileData=fileDataService.uploadFile(file);
                jobSeeker.setResume(fileData);
                jobSeekerRepository.save(jobSeeker);
                return fileMapper.toDto(fileData);
            }
    }


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
        jobSeeker1.setFirstname(jobSeeker.getFirstname());
        jobSeeker1.setLastname(jobSeeker.getLastname());

        jobSeeker1.setEmail(jobSeeker.getEmail());
        jobSeeker1.setPassword(jobSeeker.getPassword());
        jobSeeker1.setRole(Role.JOB_SEEKER);
        jobSeekerRepository.save(jobSeeker1);

        return new JobSeekerResponse(jobSeeker1.getId(), jobSeeker1.getFirstname(), jobSeeker1.getLastname(), jobSeeker1.getRole());
    }


    @Override
    public JobSeekerResponses update(Long id, JobSeekerRequests jobSeeker) {


        JobSeeker jobSeeker1 = jobSeekerRepository.findById(id).orElseThrow(() -> new RuntimeException("user can be null"));
        System.out.println(jobSeeker.toString());
        jobSeeker1.setImage(
                jobSeeker.getImageId() == null ? null :
                        storageRepository.findById(jobSeeker.getImageId()).orElseThrow());
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
        jobSeeker1.setPosition(positionRepository.findByName(jobSeeker.getPosition()));
        jobSeeker1.setWorking_place(jobSeeker.getWorking_place());
        jobSeeker1.setSkills(jobSeeker.getSkills());
//        if (jobSeeker.getResumeId() != null){
//            jobSeeker1.setResume(fileRepository.findById(jobSeeker.getResumeId()).orElseThrow());
//        }
        jobSeeker1.setRole(Role.JOB_SEEKER);

        jobSeekerRepository.save(jobSeeker1);

        return new JobSeekerResponses(jobSeeker1.getId(),
                jobSeeker1.getImage() == null ? null :
                        jobSeeker1.getImage().getId(),
                jobSeeker1.getFirstname(),
                jobSeeker1.getLastname(),
                jobSeeker1.getAbout(),
                jobSeeker1.getEducation(),
                jobSeeker1.getInstitution(),
                jobSeeker1.getMonth(),
                jobSeeker1.getYear(),
                jobSeeker1.getPosition().getName(),
                jobSeeker1.getWorking_place(),
                jobSeeker1.getResume() == null ? null :
                        jobSeeker1.getResume().getId(), jobSeeker1.getBirthday(), jobSeeker1.getCountry(),
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
            System.out.println(jobSeeker.getImage().getId() + "1q1\n\n\n");
            jobSeeker.setImage(null);
            ImageData save = storageService.uploadImage(file, image);
            jobSeeker.setImage(save);
            System.out.println(jobSeeker.getImage().getId() + "2q2\n\n\n");

            jobSeekerRepository.save(jobSeeker);
        } else {
            ImageData image = storageService.uploadImage(file);
            jobSeeker.setImage(image);
            jobSeekerRepository.save(jobSeeker);
        }

        return null;
    }

    @Override
    public List<JobSeeker> filterJobSeekers(
            Position position,
            Education education,
            String country,
            String city,
            Experience experience
    ) {
        if (position == null && education == null && country == "" &&
                city == "" && experience == null) {
            return jobSeekerRepository.findAll();
        }
        // Call the custom query method defined in the repository
        return jobSeekerRepository.filterJobSeekers(
                position != null ? position : null,
                education,
                country != null && !country.isEmpty() ? country : null,
                city != null && !city.isEmpty() ? city : null,
                experience
        );
    }

    @Override
    public List<JobSeeker> searchByFirstAndLastName(String firstname, String lastname) {


        return jobSeekerRepository.searchJobSeekers(
                firstname != null && !firstname.isEmpty() ? firstname : null,
                lastname != null && !lastname.isEmpty() ? lastname : null

        );
    }

    @Override
    public void saveImage(MultipartFile multipartFile) throws IOException {
        String folder = "/Users/bambook/Desktop/hr_system_back/src/main/resources/templates/folder/";
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(folder + multipartFile.getOriginalFilename());
        Files.write(path, bytes);

    }
}
