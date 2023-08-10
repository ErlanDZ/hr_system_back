package com.example.hr_system.controller;

import com.example.hr_system.dto.file.FileResponse;
import com.example.hr_system.dto.jobSeeker.*;
import com.example.hr_system.dto.notification.NotificationResponse;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.User;
import com.example.hr_system.entities.Vacancy;
import com.example.hr_system.mapper.FileMapper;
import com.example.hr_system.mapper.NotificationMapper;

import com.example.hr_system.mapper.JobSeekerMapper;

import com.example.hr_system.repository.FileRepository;
import com.example.hr_system.repository.JobSeekerRepository;
import com.example.hr_system.repository.UserRepository;
import com.example.hr_system.service.FileDataService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.VacancyService;
import com.example.hr_system.service.impl.FileDataServiceImpl;
import com.example.hr_system.service.impl.JobSeekerServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.providers.HateoasHalProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("job_seeker/")
//@PreAuthorize("hasAnyAuthority('JOB_SEEKER')")
@AllArgsConstructor
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;
    private final JobSeekerServiceImpl jobSeekerServiceImpl;
    private final FileDataService service;
    private final VacancyService vacancyService;
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final FileDataServiceImpl fileDataService;
    private final NotificationMapper notificationMapper;
    public final JobSeekerMapper jobSeekerMapper;
    private final JobSeekerRepository jobSeekerRepository;

//
//    @PostMapping("resume/downloadUrl")
//    public ResponseEntity<String> fileuploadUrl(@RequestParam String extension){
//        return ResponseEntity.ok(fileDataService.generateUrl(UUID.randomUUID()+"."+extension, HttpMethod.PUT));
//    }
//    @GetMapping("resume/getDownloadUrl")
//    public ResponseEntity<String> getFileuploadUrl(@RequestParam String filename){
//        return ResponseEntity.ok(fileDataService.generateUrl(filename, HttpMethod.GET));
//    }
    @GetMapping("/get/jobseeker/{jobSeekerId}")
    public JobSeekerResponses jobSeekerResponses(@PathVariable Long jobSeekerId){
        return jobSeekerMapper.toDto(jobSeekerRepository.findById(jobSeekerId).orElseThrow(()->
                new NotFoundException("not found jobseeker with id: "+ jobSeekerId)));
    }

    @PostMapping("resume/upload/{id}")
    public ResponseEntity<?> uploadResume(@RequestParam("resume") MultipartFile file, @PathVariable Long id) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSeekerService.uploadResume(file, id));
    }

    @GetMapping("/resume/{id}")
    public void getFileById(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        service.getFileById(id, httpServletResponse);
    }

    @GetMapping("/download/file/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse http) throws IOException {
        service.downloadFile(id, http);
    }


    @PostMapping("/create")
    public JobSeekerResponse save(@RequestBody JobSeekerRequest jobSeeker) {
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
    public List<Vacancy> getVacancies() {
        return vacancyService.getAll();
    }


    @PutMapping("/responded/{vacancyId}/{userId}")
    public VacancyResponse respondedForVacancy(@PathVariable Long vacancyId, @PathVariable Long userId) {
        return vacancyService.responded(vacancyId, userId);
    }
@GetMapping("/notifications/{userId}")
    public List<NotificationResponse> findAllNotificationsByUserId(@PathVariable Long userId) {
        return jobSeekerService.findAllNotificationsByUserId(userId);
    }
}
