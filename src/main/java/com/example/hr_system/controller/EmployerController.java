package com.example.hr_system.controller;


import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.cand.EducationDto;
import com.example.hr_system.dto.category.CategoryResponse;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.experience.ExperienceResponse;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.dto.jobSeeker.RespondedResponse;
import com.example.hr_system.dto.notification.NotificationResponse;
import com.example.hr_system.dto.position.CandidateResponse;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.*;
import com.example.hr_system.enums.*;
import com.example.hr_system.mapper.*;
import com.example.hr_system.repository.*;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.NotificationService;
import com.example.hr_system.service.VacancyService;
import com.example.hr_system.service.emailSender.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/employer")
@CrossOrigin(origins = "*")

//@PreAuthorize("hasAnyAuthority('EMPLOYER')")
@RequiredArgsConstructor
public class EmployerController {
    private final EmployerService employerService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final VacancyService vacancyService;
    private final JobSeekerService jobSeekerService;
    private final FileRepository fileRepository;
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final JobSeekerRepository jobSeekerRepository;
    private final ExperienceMapper experienceMapper;
    private final ExperienceRepository experienceRepository;
    private final JobSeekerMapper jobSeekerMapper;
    private final UserRepository userRepository;
    private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;
    private final EmailSenderService emailSenderService;
    private final NotificationService notificationService;
    private final EmployerRepository employerRepository;



    @GetMapping("profile/{id}")
    public EmployerResponses getEmployerById(@PathVariable Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User not found!" + id));
        Long employerId = user.getEmployer().getId();
        return employerService.getById(employerId);
    }

    @GetMapping("/invite/{jobSeekerId}/{employerId}/{fileId}")
    public boolean inviteJobSeekerWithFile(@PathVariable Long jobSeekerId,
                                           @PathVariable Long employerId,
                                           @PathVariable Long fileId,
                                           @RequestParam String message) throws MessagingException {
        String userEmail = userRepository.findById(employerId).orElseThrow(() ->
                new NotFoundException("userId not found!")).getEmail();
        String jobSeekerEmail = userRepository.findById(jobSeekerId).orElseThrow(() ->
                new NotFoundException("userId not found!")).getEmail();
        FileData fileData = fileRepository.findById(fileId).orElseThrow(() ->
                new NotFoundException("userId not found!"));


        emailSenderService.inviteJobSeeker(userEmail, jobSeekerEmail, message, fileData);

        return true;
    }

    @GetMapping("/invite/{jobSeekerId}/{employerId}")
    public boolean inviteJobSeeker(@PathVariable Long jobSeekerId,
                                   @PathVariable Long employerId, @RequestParam String message) {
        String userEmail = userRepository.findById(employerId).orElseThrow(() ->
                new NotFoundException("userId not found!")).getEmail();
        String jobSeekerEmail = userRepository.findById(jobSeekerId).orElseThrow(() ->
                new NotFoundException("userId not found!")).getEmail();
        emailSenderService.inviteJobSeeker(userEmail, jobSeekerEmail, message);

        return true;
    }

    @PostMapping("update/employer/{id}")
    public EmployerResponses updateEmployer(@PathVariable Long id, @RequestBody EmployerRequests employerRequests) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User not found!" + id));
        Long employerId = user.getEmployer().getId();
        return employerService.update(employerId, employerRequests);
    }

    @GetMapping("/positions")
    public List<CandidateResponse> positions() {
        return positionMapper.listCandidatePositionToDto(positionRepository.findAll());
    }

    @GetMapping("/categories")
    List<CategoryResponse> getAllCategories() {
        return categoryMapper.toDtos(categoryRepository.findAll());
    }

    @GetMapping("/educations")
    public List<EducationDto> education() {
        List<EducationDto> strings = new ArrayList<>();
        for (Education education : Education.values()) {
            EducationDto dto = new EducationDto();
            dto.setName(education.name());
            strings.add(dto);
        }
        return strings;
    }

    @GetMapping("/candidate/filter")
    public List<CandidateResponses> filter2(@RequestParam String position,
                                           @RequestParam String education, @RequestParam String country,
                                           @RequestParam String city, @RequestParam String experience) {
        List<CandidateResponses> candidateResponses
                = jobSeekerMapper.listConvertToCandidateResponse(
                jobSeekerService.filterJobSeekers(positionRepository.findByName(position),
                        employerService.containsEducation(education) ?
                                Education.valueOf(education) :
                                Education.BACHELOR,
                        country, city, experienceRepository.findByName(experience)));
        return candidateResponses;
    }

    @GetMapping("/search")
    public List<CandidateResponses> search(@RequestParam String name) {
        return jobSeekerMapper.listConvertToCandidateResponse(
                jobSeekerRepository.searchByName(name));
    }


    @GetMapping("/candidate/{employerId}")
    public List<CandidateResponses> candidateResponses(@PathVariable Long employerId) {
        User user = userRepository.findById(employerId).orElseThrow(() -> new NotFoundException("not found user"));
        return employerService.getAllCandidates(user.getEmployer().getId());
    }

    @PostMapping("/candidate/favorite/{userId}")
    public boolean setFavorite(@PathVariable Long userId, @RequestParam Long jobSeekerId) {
        System.out.println("function is working\n\n\n");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found!" + userId));
        Long employerId = user.getEmployer().getId();
        return employerService.selectToFavorites(jobSeekerId, employerId);
    }

    @GetMapping("/candidate/favorites/{userId}")
    public List<CandidateResponses> getEmployerFavorites(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found!" + userId));
        Employer employer = user.getEmployer();

        return employerService.favoriteCandidateResponses(employer);
    }

    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZHNlQGdtYWlsLmNvbSIsImlhdCI6MTY4OTkyOTE0NSwiZXhwIjoxNjkwMDE1NTQ1fQ.C71FQIBPZOgADqjvYqY1Ddy0jbCh-c36ADQCFMw8SMc
    @GetMapping("employers")
    public List<EmployerResponses> getAllEmployers() {
        return employerService.getAll();
    }

    @GetMapping("/vacancies/{userId}")
    public List<VacancyResponse> getAllMyVacancies(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found!" + userId));
        Long employerId = user.getEmployer().getId();

        return vacancyService.getMyVacancies(employerId);
    }
// TODO
//    @PostMapping("/vacancy/{vacancyId}")
//    public VacancyResponse updateEmployerVacancy(@PathVariable Long employerId ,@PathVariable Long
//            vacancyId, @RequestBody VacancyRequest vacancyRequest) {
//        return vacancyService.updateEmployerVacancyByIds(employerId,vacancyId, vacancyRequest);
//    }

    @PostMapping("/update/vacancy/{vacancyId}")
    public VacancyResponse updateVacancy(@PathVariable Long vacancyId, @RequestBody VacancyRequest vacancyRequest) {


        return vacancyService.update(vacancyId, vacancyRequest);
    }

    @GetMapping("/job_seekers")
    public List<JobSeekerResponses> getAllJobSeekers() {
        return jobSeekerService.getAllJobSeekers();
    }

    @GetMapping("/vacancy/{vacancyId}")
    public VacancyResponse getByIdVacancy(@PathVariable Long vacancyId) {
        return vacancyMapper.toDto(vacancyRepository.findById(vacancyId).orElseThrow(() ->
                new NotFoundException("vacancy not found with id: " + vacancyId)));
    }

    @PostMapping("/vacancy/{employerId}")
    public VacancyResponse save(@PathVariable Long employerId, @RequestBody VacancyRequest vacancyRequest) {
        return vacancyService.saveVacancy(employerId, vacancyRequest);
    }


    @DeleteMapping("/delete/{vacancyId}")
    public boolean delete(@PathVariable("vacancyId") Long vacancyId) {

        vacancyService.delete(vacancyId);
        return true;
    }

    @GetMapping("/vacancy/search")
    public List<JobSeekerVacanciesResponses> vacancySearch(@RequestParam(required = false) String search) {
        return vacancyService.searchVacancy(search);
    }


    @GetMapping("/vacancy/filter")
    public List<JobSeekerVacanciesResponses> filter(@RequestParam(required = false) String category, @RequestParam(required = false) String position, @RequestParam(required = false) String country,
                                                    @RequestParam(required = false) String city, @RequestParam(required = false) String experience) {

        return vacancyService.filter(category, position, country, city, experience);
    }

    @PutMapping("/newStatusForVacancy/{vacancyId}")
    public void newStatus(@PathVariable Long vacancyId, @RequestParam(required = false) String status){
        vacancyService.setStatusOfVacancy(vacancyId,status);
         }


    @PutMapping("/setStatusForJobSeeker/{vacancyId}/{userId}")
    public void setStatusForJobSeeker(@PathVariable Long vacancyId, @PathVariable Long userId, @RequestParam(required = false) String status) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("user not found!" + userId));
        Long jobSeekerId = user.getJobSeeker() == null ? null :
                user.getJobSeeker().getId();

        vacancyService.setStatusOfJobSeeker(vacancyId, jobSeekerId, status);
    }

    @GetMapping("/list/responded/{vacancyId}")
    public List<RespondedResponse> responded(@PathVariable Long vacancyId) {
        return vacancyService.listForResponded(vacancyId);
    }

    @GetMapping("/list/responded/filter/{vacancyId}")
    public List<RespondedResponse> filter(@PathVariable Long vacancyId, @RequestParam String statusOfJobSeeker, String experience,
                                          String localDate) {

        return vacancyService.listForResponded(vacancyId,
                employerService.containsStatusOfJobSeeker(statusOfJobSeeker) ? statusOfJobSeeker : null, experience,
                localDate
        );
    }
    @GetMapping("/list/responded/search/{vacancyId}")
    public List<RespondedResponse> searchResponded(@PathVariable Long vacancyId,@RequestParam String names) {

        return vacancyService.listForResponded(vacancyId, jobSeekerRepository.searchByName(names));
    }

    //    OPEN,
    //    ARCHIVE,
    //    CLOSED,
    @GetMapping("/statusOfVacancy")
    public StatusOfVacancy[] status() {
        return StatusOfVacancy.values();
    }

    //    FULL,GIBRID,FIXED
    @GetMapping("/typeofEmployments")
    public List<String> responseEntity() {
        List<String> strings = new ArrayList<>();
        strings.add(String.valueOf(TypeOfEmployment.NEPOLNIY_RABOCHIY_DEYN));
        strings.add(String.valueOf(TypeOfEmployment.POLNIY_RABOCHIY_DEN));
        strings.add(String.valueOf(TypeOfEmployment.UDALENNAYA_RABOTA));
        return strings;
    }

    @GetMapping("/experience")
    public List<ExperienceResponse> experienceResponses() {
        return experienceMapper.listExperienceResponseToDto(experienceRepository.findAll());
    }

    @GetMapping("/statusOfJobSeekerForVacancy")
    public List<String> statusOfJobSeekers() {
        List<String> strings = new ArrayList<>();
        strings.add(String.valueOf(StatusOfJobSeeker.AN_INTERVIEW));
        strings.add(String.valueOf(StatusOfJobSeeker.ACCEPT));
        strings.add(String.valueOf(StatusOfJobSeeker.OFFER));
        strings.add(String.valueOf(StatusOfJobSeeker.REJECT));
        strings.add(String.valueOf(StatusOfJobSeeker.AN_INTERVIEW));
        return strings;
    }

    @GetMapping("/byApplicationDate")
    public ApplicationDate[] applicationDates() {
        return ApplicationDate.values();
    }


    // IMAGE AND FILE UPLOAD

    @PostMapping("resume/upload/{employerId}")
    public ResponseEntity<?> uploadResume(@RequestBody MultipartFile file, @PathVariable Long employerId) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(employerService.uploadResume(file, employerId));
    }

    @GetMapping("/salaryType")
    public List<String> salaryType() {
        List<String> strings = new ArrayList<>();
        strings.add(String.valueOf(SalaryType.GIBRID));
        strings.add(String.valueOf(SalaryType.FULL));
        strings.add(String.valueOf(SalaryType.FIXED));
        return strings;
    }

    @GetMapping("getValute")
    public List<String> valute() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(Valute.USD));
        list.add(String.valueOf(Valute.EUR));
        list.add(String.valueOf(Valute.KZT));
        list.add(String.valueOf(Valute.SOM));
        list.add(String.valueOf(Valute.RUB));
        list.add(String.valueOf(Valute.UZB));
        return list;
    }


    @GetMapping("/sendToEmail/{employerId}/{jobSeekerId}/{fileId}")
    public String send(@PathVariable Long employerId, @PathVariable Long jobSeekerId,
                       @PathVariable Long fileId,
                       @RequestParam String message) throws MessagingException {
        FileData fileData = fileRepository.findById(fileId).orElseThrow();
        Employer employer = employerRepository.findById(employerId).orElseThrow();
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId).orElseThrow();
        emailSenderService.inviteJobSeeker(employer.getEmail(), jobSeeker.getEmail(), message,
                fileData);
        return "ok";
    }

}
