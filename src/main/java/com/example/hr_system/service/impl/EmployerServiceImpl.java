package com.example.hr_system.service.impl;

import com.example.hr_system.dto.employer.EmployerRequest;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponse;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.SimpleResponse;
import com.example.hr_system.dto.file.FileResponse;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.entities.*;
import com.example.hr_system.enums.ApplicationDate;
import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.StatusOfJobSeeker;
import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.mapper.FileMapper;
import com.example.hr_system.repository.EmployerRepository;
import com.example.hr_system.repository.JobSeekerRepository;
import com.example.hr_system.repository.UserRepository;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.mapper.EmployerMapper;

import com.example.hr_system.service.FileDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {


    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final EmployerMapper employerMapper;
    private final UserRepository userRepository;
    private final FileDataService fileDataService;
    private final FileMapper fileMapper;


    @Override
    public boolean selectToFavorites(Long jobSeekerId, Long employerId)throws NotFoundException{

            List<JobSeeker> jobSeekers =
                    employerRepository.findById(employerId).get().getFavorites();
            JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId).get();
           // for (JobSeeker jobSeeker: jobSeekers){
                if (!jobSeekers.contains(jobSeeker)){
                    jobSeekers.add(jobSeeker);
                    System.out.println("adding to favorites\n\n\n");
                }
                else if (jobSeekers.contains(jobSeeker)){
                    jobSeekers.remove(jobSeeker);
                    System.out.println("!adding to favorites\n\n\n");
                }
          //  }
//            if (jobSeekers.size()==0){
//                jobSeekers.add(jobSeekerRepository.findById())
//            }

            Employer employer= employerRepository.findById(employerId).orElseThrow(()->
                    new NotFoundException("not found employer with id:"+employerId));
            employer.setFavorites(jobSeekers);

            employerRepository.save(employer);

        return true;
    }

    @Override
    public List<CandidateResponses> favoriteCandidateResponses(Employer employer){
        return candidateToDTOs(employer.getFavorites(), employer.getId());
    }


    @Override
    public List<CandidateResponses> getAllCandidates(Long employerId){


        return candidateToDTOs(jobSeekerRepository.findAll(), employerId);
    }
    @Override

    public List<EmployerResponses> getAll() {

        return employerMapper.toDtos(employerRepository.findAll());
    }



    public CandidateResponses convertEntityToCandidateResponse(JobSeeker jobSeeker){
        if (jobSeeker == null){
            System.out.println("its null\n\n\n");
            return null;
        }
        CandidateResponses candidateResponses = new CandidateResponses();
        if (jobSeeker.getResume()==(null)){
        }
        else {
            candidateResponses.setImageId(jobSeeker.getResume().getId());

        }
        candidateResponses.setCandidateId(jobSeeker.getId());
        candidateResponses.setFirstname(jobSeeker.getFirstname());
        candidateResponses.setLastname(jobSeeker.getLastname());

        candidateResponses.setPosition(
                jobSeeker.getPosition()==null? null:
                jobSeeker.getPosition().getName());
        candidateResponses.setExperience(jobSeeker.getExperience()==null?null:
                jobSeeker.getExperience().getName());
        candidateResponses.setCity(jobSeeker.getCity());
        candidateResponses.setCountry(jobSeeker.getCountry());

        return candidateResponses;
    }



    public List<CandidateResponses> candidateToDTOs(List<JobSeeker>jobSeekers, Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow(()-> new NotFoundException("njsd"));
        List<CandidateResponses>candidateResponses=new ArrayList<>();
        for (JobSeeker jobSeeker:jobSeekers) {
            CandidateResponses responses = convertEntityToCandidateResponse(jobSeeker);
            if(employer.getFavorites().contains(jobSeeker)) {
                responses.setRed(true);
            }
            candidateResponses.add(responses);
        }

        return candidateResponses;
    }

    @Override
    public EmployerResponse save(EmployerRequest employerRequest) {
        Employer employer = new Employer();
        employer.setCompanyName(employerRequest.getCompanyName());
        employer.setEmail(employerRequest.getEmail());
        employer.setPassword(employerRequest.getPassword());
        employerRepository.save(employer);

        EmployerResponse employerResponse = new EmployerResponse();
        employerResponse.setCompanyName(employerRequest.getCompanyName());
        employerResponse.setId(employer.getId());

        return employerResponse;
    }

    @Override
    public EmployerResponses update(Long id, EmployerRequests employerRequests) {
        EmployerResponses employerResponses = getById(id);
        Employer employer;

        employer = convertToEntity(id, employerRequests);
        employerResponses =  employerMapper.toDto(employer);
        employerRepository.save(employer);

        return employerResponses;
    }
    @Override
    public String getTimeLeft(String creationDateVacancy) {

        if (creationDateVacancy == null) {
            return "N/A";
        }

        LocalDateTime creationDate = LocalDateTime.parse(creationDateVacancy);
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(creationDate, now);
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();

        return String.valueOf(days>0?days +" days":hours>0?hours+" hours":minutes+" minutes");
        //return String.valueOf(minutes);
    }

    @Override
    public EmployerResponses getById(Long id) {
        return employerMapper.toDto(employerRepository.findById(id).orElseThrow(() -> new NotFoundException("we don't have employer with id :" + id)));
    }
    @Override
    public SimpleResponse deleteById(Long id) {
        boolean emp = employerRepository.existsById(id);
        if (!emp) {
            throw new RuntimeException("we don't have employer with id :" + id);
        }
        employerRepository.deleteById(id);
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage("You deleted employer successfully!!!");
        return simpleResponse;
    }
    @Override
    public Employer convertToEntity(Long id, EmployerRequests employerRequests) {
        if (employerRequests == null) {
            return null;
        }
        Employer employer = employerRepository.findById(id).get();

        employer.setAboutCompany(employerRequests.getAboutCompany());
        employer.setCountry(employerRequests.getCountry());
        employer.setCity(employerRequests.getCity());
        employer.setAddress(employerRequests.getAddress());
        employer.setEmail(employerRequests.getEmail());
        employer.setPhoneNumber(employerRequests.getPhoneNumber());
        return employer;
    }





    @Override
    public List<CandidateResponses> filter(String position, String education, String country, String city, String experience) {

        return null;
    }

    @Override
    public FileResponse uploadResume(MultipartFile file, Long id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("user not found!" + id));
        Employer employer = user.getEmployer();

        if (employer.getResume() != null) {
            FileData fileData = new FileData();
            fileData = employer.getResume();
            employer.setResume(null);
            FileData save = fileDataService.uploadFile(file, fileData);
            employer.setResume(save);
            employerRepository.save(employer);
            return fileMapper.toDto(save);
        } else {
            FileData fileData=fileDataService.uploadFile(file);
            employer.setResume(fileData);
            employerRepository.save(employer);
            return fileMapper.toDto(fileData);
        }
    }

    @Override
    public boolean containsEducation(String str) {
        for (Education education : Education.values()) {
            if (education.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean containsTypeOfEmployment(String str) {
        for (TypeOfEmployment education : TypeOfEmployment.values()) {
            if (education.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsStatusOfJobSeeker(String str) {
        for (StatusOfJobSeeker education : StatusOfJobSeeker.values()) {
            if (education.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsApplicationDate(String str) {
        for (ApplicationDate education : ApplicationDate.values()) {
            if (education.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }


}
