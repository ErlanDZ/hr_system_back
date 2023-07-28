package com.example.hr_system.controller;

import com.example.hr_system.dto.jobSeeker.*;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.User;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.mapper.FileMapper;
import com.example.hr_system.repository.FileRepository;
import com.example.hr_system.repository.StorageRepository;
import com.example.hr_system.repository.UserRepository;
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
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("job_seeker/")
//@PreAuthorize("hasAnyAuthority('JOB_SEEKER')")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;
    private final JobSeekerServiceImpl jobSeekerServiceImpl;
    private final StorageService service;
    private final VacancyService vacancyService;
    private final StorageRepository storageRepository;
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;


    @PostMapping("resume/upload/{id}")
    public ResponseEntity<?> uploadResume(@RequestParam("resume") MultipartFile file,@PathVariable Long id) throws IOException {

       // User user = userRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSeekerService.uploadResume(file,id));
    }
    @GetMapping("/resume/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id){
        System.out.println("asghjd");
        return service.downloadFile(id);
    }

    @PostMapping("image/upload/{id}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file,@PathVariable Long id) throws IOException {


        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSeekerService.uploadImage(file,id));
    }

    @GetMapping("image/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id){
        System.out.println("asghjd");
        return service.downloadImage(id);
    }

    @PostMapping("/create")
    public JobSeekerResponse save(@RequestBody JobSeekerRequest jobSeeker){
        return jobSeekerService.save(jobSeeker);
    }



    @PostMapping("/update/jobseeker/{id}")
    public JobSeekerResponses update(@PathVariable("id") Long id, @RequestBody JobSeekerRequests jobSeeker) throws IOException {
        if (jobSeeker.getImageId() == null) {
            new NotFoundException("we could not found image");

        }
        return jobSeekerService.update(id, jobSeeker);

    }

    @GetMapping("/vacancies")
    public List<Vacancy> getVacancies(){
        return vacancyService.getAll();
    }

    @GetMapping("/list/responded/{vacancyId}")
    public List<RespondedResponse> responded(@PathVariable Long vacancyId) {
        return vacancyService.listForResponded(vacancyId);
    }

    @PutMapping("/responded/{vacancyId}/{jobSeekerId}")
    public void respondedForVacancy(@PathVariable Long vacancyId, @PathVariable Long jobSeekerId) {
        vacancyService.responded(vacancyId, jobSeekerId);
    }





}
