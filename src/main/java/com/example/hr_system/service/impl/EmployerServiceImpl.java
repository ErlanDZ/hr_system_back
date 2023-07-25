package com.example.hr_system.service.impl;

import com.example.hr_system.dto.employer.EmployerRequest;
import com.example.hr_system.dto.employer.EmployerRequests;
import com.example.hr_system.dto.employer.EmployerResponse;
import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.dto.SimpleResponse;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.CandidateResponses;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.repository.EmployerRepository;
import com.example.hr_system.repository.JobSeekerRepository;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.mapper.EmployerMapper;

import com.example.hr_system.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {


    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final EmployerMapper employerMapper;
    private final StorageService storageService;


    @Override
    public boolean selectToFavorites(Long jobSeekerId, Long employerId)throws NotFoundException{
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId).orElseThrow();
            List<JobSeeker> jobSeekers =
                    employerRepository.findById(employerId).get().getFavorites();
            jobSeeker.setIsFavorite(employerId);
        System.out.println(jobSeeker.getIsFavorite()+"\n\n\n");
        jobSeekers.add(jobSeeker);

            Employer employer= employerRepository.findById(employerId).get();
            employer.setFavorites(jobSeekers);
            employerRepository.save(employer);

        return true;
    }

    @Override
    public List<CandidateResponses> favoriteCandidateResponses(Long employerId){
        return candidateToDTOs(employerRepository.findById(employerId).get().getFavorites(), employerId);
    }


    @Override
    public List<CandidateResponses> getAllCandidates(){


        return candidateToDTOs(jobSeekerRepository.findAll());
    }
    @Override

    public List<EmployerResponses> getAll() {

        return employerMapper.toDtos(employerRepository.findAll());
    }



    public CandidateResponses convertEntityToCandidateResponse(JobSeeker jobSeeker){
        if (jobSeeker == null){
            return null;
        }
        CandidateResponses candidateResponses = new CandidateResponses();
        candidateResponses.setIsFavorite(jobSeeker.getIsFavorite());
        candidateResponses.setImage(imageToResponse(jobSeeker.getImage()));
        candidateResponses.setFirstname(jobSeeker.getFirstname());
        candidateResponses.setLastname(jobSeeker.getLastname());
//        candidateResponses.setPosition(jobSeeker.getPosition());
//        candidateResponses.setExperience(jobSeeker.getYear());
        candidateResponses.setCountry(jobSeeker.getCountry());
        candidateResponses.setCity(jobSeeker.getCity());

        return candidateResponses;
    }

    @Override
    public Response imageToResponse(ImageData image) {
        Response response = new Response();
        response.setId(image.getId());
        response.setName(image.getName());
        response.setType(image.getType());
        response.setImageData(imageToResponse(image));
        response.setJobSeekerId(image.getJobSeeker().getId());

        return response;
    }

    public List<CandidateResponses> candidateToDTOs(List<JobSeeker>jobSeekers){
        List<CandidateResponses>candidateResponses=new ArrayList<>();
        for (JobSeeker jobSeeker:jobSeekers) {
            candidateResponses.add(convertEntityToCandidateResponse(jobSeeker));
        }

        return candidateResponses;
    }
    public List<CandidateResponses> candidateToDTOs(List<JobSeeker>jobSeekers, Long employerId){
        List<CandidateResponses>candidateResponses=new ArrayList<>();
        for (JobSeeker jobSeeker:jobSeekers) {
            if (jobSeeker.getIsFavorite()==employerId){
                candidateResponses.add(convertEntityToCandidateResponse(jobSeeker));
            }
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
    public EmployerResponses getById(Long id) {
        return employerMapper.toDto(employerRepository.findById(id).orElseThrow(() -> new RuntimeException("we don't have employer with id :" + id)));
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
    public ImageData responseToImage(Response image) {
        ImageData imageData = new ImageData();
        imageData.getJobSeeker().setId(image.getJobSeekerId());
        imageData.setImageData(imageData.getImageData());
        imageData.setName(image.getName());
        imageData.setId(image.getId());
        imageData.setType(image.getType());
        return imageData;
    }

    @Override
    public Response uploadImage(MultipartFile file, Long id) throws IOException {
        Employer employer = employerRepository.findById(id).orElseThrow(()-> new NotFoundException("employer not found!"));
        if (employer.getImage() != null) {
            ImageData image = employer.getImage();
            employer.setImage(null);
            ImageData save = storageService.uploadImage(file, image);
            employer.setImage(save);
            employerRepository.save(employer);
        } else {
            ImageData image = storageService.uploadImage(file);
            employer.setImage(image);
            employerRepository.save(employer);
        }

        return null;
    }
}
