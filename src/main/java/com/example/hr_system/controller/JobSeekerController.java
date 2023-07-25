package com.example.hr_system.controller;

import com.example.hr_system.dto.jobSeeker.JobSeekerRequest;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequests;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponse;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.StorageService;
import com.example.hr_system.service.VacancyService;
import com.example.hr_system.service.impl.JobSeekerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
//@PreAuthorize("hasAnyAuthority('JOB_SEEKER')")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;
    private final JobSeekerServiceImpl jobSeekerServiceImpl;
    private final StorageService service;
    private final VacancyService vacancyService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file,@PathVariable Long id) throws IOException {


        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSeekerService.uploadImage(file,id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id){
        System.out.println("asghjd");
        return service.downloadImage(id);
    }

    @PostMapping("/create")
    public JobSeekerResponse save(@RequestBody JobSeekerRequest jobSeeker){
        return jobSeekerService.save(jobSeeker);
    }



    @PostMapping("/update/jobseeker/{id}")
    public JobSeekerResponses update(@PathVariable("id") Long id,
                                     @RequestParam("image") MultipartFile file, @RequestBody JobSeekerRequests jobSeeker) throws IOException {
        if(file!=null){
            ImageData uploadImage = service.uploadImage(file);
            return jobSeekerServiceImpl.updateWithImage(id,jobSeeker, uploadImage);

        }

        return jobSeekerService.update(id,jobSeeker);
    }

    @GetMapping("/vacancies")
    public List<Vacancy> getVacancies(){
        return vacancyService.getAll();
    }





}
