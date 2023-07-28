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
import com.example.hr_system.entities.Employer;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.User;
import com.example.hr_system.enums.Education;
import com.example.hr_system.mapper.ExperienceMapper;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.mapper.PositionMapper;
import com.example.hr_system.mapper.VacancyMapper;
import com.example.hr_system.repository.*;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.service.StorageService;
import com.example.hr_system.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

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
    private final UserRepository userRepository;
    private final VacancyMapper vacancyMapper;
    private  final VacancyRepository vacancyRepository;


    @GetMapping("profile/{id}")
    public EmployerResponses getEmployerById(@PathVariable Long id){

        User user = userRepository.findById(id).orElseThrow(()->
                new NotFoundException("User not found!"+id));
        Long employerId = user.getEmployer().getId();
        return employerService.getById(employerId);
    }

    @PutMapping("update/employer/{id}")
    public EmployerResponses updateEmployer(@PathVariable Long id, @RequestBody EmployerRequests employerRequests) {
        User user = userRepository.findById(id).orElseThrow(()->
                new NotFoundException("User not found!"+id));
        Long employerId = user.getEmployer().getId();
        return employerService.update(employerId, employerRequests);
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
    public List<CandidateResponses> search(@RequestParam String name){
        return jobSeekerMapper.listConvertToCandidateResponse(
                jobSeekerRepository.searchByName(name));
    }

    @GetMapping("/experience")
    public List<ExperienceResponse> experienceResponses(){
        return experienceMapper.listExperienceResponseToDto(experienceRepository.findAll());
    }
    @GetMapping("/candidate")
    public List<CandidateResponses> candidateResponses(){
        return employerService.getAllCandidates();
    }
    @PostMapping("/candidate/favorite/{userId}")
    public boolean setFavorite(@PathVariable Long userId,@RequestParam Long jobSeekerId){
        User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("User not found!"+userId));
        Long employerId = user.getEmployer().getId();
        return employerService.selectToFavorites(jobSeekerId, employerId);
    }

    @GetMapping("/candidate/favorites/{userId}")
    public List<CandidateResponses> getEmployerFavorites(@PathVariable Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("User not found!"+userId));
        Long employerId = user.getEmployer().getId();

        return employerService.favoriteCandidateResponses(employerId);
    }
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZHNlQGdtYWlsLmNvbSIsImlhdCI6MTY4OTkyOTE0NSwiZXhwIjoxNjkwMDE1NTQ1fQ.C71FQIBPZOgADqjvYqY1Ddy0jbCh-c36ADQCFMw8SMc
    @GetMapping("employers")
    public List<EmployerResponses> getAllEmployers(){
        return employerService.getAll();
    }

    @GetMapping("/vacancies/{userId}")
    public List<VacancyResponse> getAllMyVacancies(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("User not found!"+userId));
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
    public List<JobSeekerResponses> getAllJobSeekers(){
        return jobSeekerService.getAllJobSeekers();
    }

    @GetMapping("/vacancy/{vacancyId}")
    public VacancyResponse getByIdVacancy(@PathVariable Long vacancyId){
        return vacancyMapper.toDto(vacancyRepository.findById(vacancyId).orElseThrow(()->
                new NotFoundException("vacancy not found with id: "+vacancyId)));
    }
    @PostMapping("/vacancy/{employerId}")
    public VacancyResponse save(@PathVariable Long employerId,@RequestBody VacancyRequest vacancyRequest) {
        User user = userRepository.findById(employerId).orElseThrow(()->
                new NotFoundException("user not found! employerController"));
        Long employerIdd = user.getEmployer().getId();
        return vacancyService.saveVacancy(employerIdd, vacancyRequest);
    }
    @GetMapping("/jobSeeker")
    public List<JobSeekerVacanciesResponses> getAllForJobSeeker() {
        return vacancyService.jobSeekerVacancies();
    }




    @PostMapping("/image/upload/{userId}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("User not found!"+userId));
        Long employerId = user.getEmployer().getId();


        return ResponseEntity.status(HttpStatus.OK)
                .body(employerService.uploadImage(file,employerId));
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId){
        System.out.println("asghjd");
        return service.downloadImage(imageId);
    }

    /// FROM VACANCY CONTROLLER






    @DeleteMapping("/delete/{vacancyId}")
    public boolean delete(@PathVariable("vacancyId") Long vacancyId){

         vacancyService.delete(vacancyId);
//         if (vacancyRepository.findById(vacancyId)==null){
//             System.out.println("its null\n\n\n");
//         }
//         else {
//             System.out.println("its not null\n\n\n");
//         }
//        if (vacancyRepository.findById(vacancyId).equals(null)){
//            System.out.println("equals work\n\n\n");
//        }

            return true;
    }

    @PutMapping("/{id}")
    public VacancyResponse update(@PathVariable Long id,@RequestBody VacancyRequest vacancy){
        return vacancyService.update(id,vacancy);
    }

    @GetMapping("/vacancy/search")
    public List<JobSeekerVacanciesResponses> vacancySearch(@RequestParam(required = false) String search){
        return vacancyService.searchVacancy(search);
    }

    @GetMapping("/vacancy/filter")
    public List<JobSeekerVacanciesResponses> filter(@RequestParam(required = false) String category, @RequestParam(required = false) String position, @RequestParam(required = false)String country,
                                                    @RequestParam(required = false)String city, @RequestParam(required = false) Experience experience){

        return vacancyService.filter(category, position, country, city, experience);
    }
    @PutMapping("/newStatusForVacancy/{vacancyId}")
    public void newStatus(@PathVariable Long vacancyId,@RequestParam(required = false) String status){
        vacancyService.setStatusOfVacancy(vacancyId,status);
    }

    @PutMapping("/setStatusForJobSeeker/{vacancyId}/{userId}")
    public void setStatusForJobSeeker(@PathVariable Long vacancyId,@PathVariable Long userId,@RequestParam(required = false) String status){
        User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("user not found!"+userId));
        Long jobSeekerId = user.getJobSeeker().getId();
        vacancyService.setStatusOfJobSeeker(vacancyId,jobSeekerId,status);
    }
}
