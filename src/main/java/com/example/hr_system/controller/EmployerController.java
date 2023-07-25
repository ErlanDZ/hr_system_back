package com.example.hr_system.controller;


import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.repository.UserRepository;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.StorageService;
import com.example.hr_system.service.VacancyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employer")
//@PreAuthorize("hasAnyAuthority('EMPLOYER')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployerController {
    private final EmployerService employerService;
    private final VacancyService vacancyService;
    private final UserRepository userRepository;
    private final JobSeekerService jobSeekerService;
    private final StorageService service;

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

    @PutMapping("update/employer/{id}")
    public EmployerResponses updateEmployer(@PathVariable Long id, @RequestBody EmployerRequests employerRequests) {
        return employerService.update(id, employerRequests);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long id) throws IOException {


        return ResponseEntity.status(HttpStatus.OK)
                .body(employerService.uploadImage(file,id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id){
        System.out.println("asghjd");
        return service.downloadImage(id);
    }
}
