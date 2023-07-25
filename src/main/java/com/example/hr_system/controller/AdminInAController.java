package com.example.hr_system.controller;

import com.example.hr_system.dto.SimpleResponse;
import com.example.hr_system.dto.employer.EmployerRequest;
import com.example.hr_system.dto.employer.EmployerResponse;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminInAController {

    //private final RestTemplate restTemplate;
    private final EmployerService employerService;
    private final JobSeekerService jobSeekerService;
    private final VacancyService vacancyService;

//    @PostMapping("employer")
//    public EmployerResponse save(@RequestBody EmployerRequest employerRequest){
//        return employerService.save(employerRequest);
//    }

    @GetMapping("employer/{id}")
    public EmployerResponses getEmployerById(@PathVariable Long id){
        return employerService.getById(id);
    }

    @GetMapping("employers")
    public List<EmployerResponses> getAllEmployers(){
        return employerService.getAll();
    }

    @DeleteMapping("delete/{id}")
    public SimpleResponse deleteEmployerById(@PathVariable Long id){
        return employerService.deleteById(id);
    }
    @GetMapping("/get/jobseeker/{id}")
    public JobSeeker getById(@PathVariable Long id) {
        return jobSeekerService.getById(id);
    }
    @DeleteMapping("/delete/jobseeker/{id}")
    public void delete(@PathVariable("id") Long id) {
        jobSeekerService.delete(id);
    }
    @GetMapping("/response/{vacancyId}")
    public void responseForVacancy(@PathVariable Long vacancyId) {
        jobSeekerService.responseForVacancy(vacancyId);
    }





}
