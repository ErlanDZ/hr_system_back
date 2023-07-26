package com.example.hr_system.controller;


import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.experience.ExperienceResponse;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.dto.position.CandidateResponse;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.enums.Education;
import com.example.hr_system.mapper.ExperienceMapper;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.mapper.PositionMapper;
import com.example.hr_system.repository.ExperienceRepository;
import com.example.hr_system.repository.JobSeekerRepository;
import com.example.hr_system.repository.PositionRepository;
import com.example.hr_system.repository.UserRepository;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.StorageService;
import com.example.hr_system.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/employer")
//@PreAuthorize("hasAnyAuthority('EMPLOYER')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployerController {
    private final EmployerService employerService;
    private final VacancyService vacancyService;
    private final JobSeekerService jobSeekerService;
    private final StorageService service;
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final JobSeekerRepository jobSeekerRepository;
    private final ExperienceMapper experienceMapper;
    private final ExperienceRepository experienceRepository;
    private final JobSeekerMapper jobSeekerMapper;


    @GetMapping("profile/{id}")
    public EmployerResponses getEmployerById(@PathVariable Long id){
        return employerService.getById(id);
    }

    @PutMapping("update/employer/{id}")
    public EmployerResponses updateEmployer(@PathVariable Long id, @RequestBody EmployerRequests employerRequests) {
        return employerService.update(id, employerRequests);
    }
    @GetMapping("/positions")
    public List<CandidateResponse> positions(){
        return positionMapper.listCandidatePositionToDto(positionRepository.findAll());
    }
    @GetMapping("/educations")
    public Education[] education(){
        return Education.values();
    }
    @GetMapping("/filter")
    public List<CandidateResponses> filter(@RequestParam String position,
                         @RequestParam String education, @RequestParam String country,
                         @RequestParam String city, @RequestParam String experience){
        List<CandidateResponses> candidateResponses
                = jobSeekerMapper.listConvertToCandidateResponse(
                        jobSeekerService.filterJobSeekers(positionRepository.findByName(position),
                Education.valueOf(education),
                country, city,experienceRepository.findByName(experience)));
        return candidateResponses;
    }
    @GetMapping("/search")
    public List<CandidateResponses> search(@RequestParam String firstname, String lastname){
        if(firstname==""&&lastname==""){
            List<JobSeeker> jobSeekers = jobSeekerRepository.findAll();
            return jobSeekerMapper.listConvertToCandidateResponse(jobSeekers);
        }
        List<CandidateResponses> candidateResponses = jobSeekerMapper.listConvertToCandidateResponse(
                jobSeekerService.searchByFirstAndLastName(firstname,lastname));

        return candidateResponses;
    }

    @GetMapping("/experience")
    public List<ExperienceResponse> experienceResponses(){
        return experienceMapper.listExperienceResponseToDto(experienceRepository.findAll());
    }
    @GetMapping("/candidate")
    public List<CandidateResponses> candidateResponses(){
        return employerService.getAllCandidates();
    }
    @PostMapping("/candidate/favorite/{employerId}")
    public boolean setFavorite(@PathVariable Long employerId,@RequestParam Long jobSeekerId){
        return employerService.selectToFavorites(jobSeekerId, employerId);
    }

    @GetMapping("/candidate/favorites/{id}")
    public List<CandidateResponses> getEmployerFavorites(@PathVariable Long id){
        return employerService.favoriteCandidateResponses(id);
    }
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZHNlQGdtYWlsLmNvbSIsImlhdCI6MTY4OTkyOTE0NSwiZXhwIjoxNjkwMDE1NTQ1fQ.C71FQIBPZOgADqjvYqY1Ddy0jbCh-c36ADQCFMw8SMc
    @GetMapping("employers")
    public List<EmployerResponses> getAllEmployers(){
        return employerService.getAll();
    }

    @GetMapping("/vacancies/{id}")
    public List<VacancyResponse> getAllMyVacancies(@PathVariable Long id) {
        return vacancyService.getMyVacancies(id);
    }
// TODO
//    @PostMapping("/vacancy/{vacancyId}")
//    public VacancyResponse updateEmployerVacancy(@PathVariable Long employerId ,@PathVariable Long
//            vacancyId, @RequestBody VacancyRequest vacancyRequest) {
//        return vacancyService.updateEmployerVacancyByIds(employerId,vacancyId, vacancyRequest);
//    }

    @PostMapping("/update/vacancy/{id}")
    public VacancyResponse updateVacancy(@PathVariable Long id, @RequestBody VacancyRequest vacancyRequest) {
        return vacancyService.update(id, vacancyRequest);
    }
    @GetMapping("/job_seekers")
    public List<JobSeekerResponses> getAllJobSeekers(){
        return jobSeekerService.getAllJobSeekers();
    }
    @PostMapping("/vacancy/{id}")
    public VacancyResponse save(@PathVariable Long id,@RequestBody VacancyRequest vacancyRequest) {
        return vacancyService.saveVacancy(id, vacancyRequest);
    }
    @GetMapping("/jobSeeker")
    public List<JobSeekerVacanciesResponses> getAllForJobSeeker() {
        return vacancyService.jobSeekerVacancies();
    }




    @PostMapping("/image/upload/{id}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long id) throws IOException {


        return ResponseEntity.status(HttpStatus.OK)
                .body(employerService.uploadImage(file,id));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id){
        System.out.println("asghjd");
        return service.downloadImage(id);
    }
}
