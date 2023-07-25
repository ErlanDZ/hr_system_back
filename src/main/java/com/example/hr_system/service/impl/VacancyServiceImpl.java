package com.example.hr_system.service.impl;

import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.*;
import com.example.hr_system.mapper.EmployerMapper;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.mapper.JobSeekerVacanciesResponsesMapper;
import com.example.hr_system.mapper.VacancyMapper;
import com.example.hr_system.repository.*;
import com.example.hr_system.service.StorageService;
import com.example.hr_system.service.VacancyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final EmployerRepository employeeRepository;
    private final StorageService storageService;
    private final VacancyRepository vacancyRepository;

    private final PositionServiceImpl positionService;

    private final VacancyMapper vacancyMapper;
    private final JobSeekerMapper jobSeekerMapper;
    private final EmployerMapper employerMapper;
    private JobSeekerVacanciesResponsesMapper jobSeekerVacanciesResponsesMapper;



    private final SalaryServiceImpl salaryService;
    private final ContactInformationServiceImpl contactInformationService;
    private final ContactInformationRepository contactInformationRepository;

    @Override
    public VacancyResponse saveVacancy(Long id,VacancyRequest vacancyRequest) {
//        employeeId = Long.valueOf(getIdFromSecurity());
        Employer employer = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyRequest.getName());
        vacancy.setDate(vacancyRequest.getDate());
        vacancy.setSkills(vacancyRequest.getSkills());
        vacancy.setDescription(vacancyRequest.getDescription());
        vacancy.setContactInfo(vacancyRequest.getContactInfo());
       // vacancy.setContactInformation(contactInformationService.convertToEntity(vacancyRequest.getContactInformation()));////
        Position position = positionService.convertToEntity(vacancyRequest.getPositionRequest());
        vacancy.setPosition(position);
        Salary salary = salaryService.convertToEntity(vacancyRequest.getSalaryRequest());
        System.out.println(vacancyRequest.getContactInformationRequest().toString() + "\n\n\n\n\n\n");

        ContactInformation contactInformation = contactInformationService.convertToEntity(vacancyRequest.getContactInformationRequest());
        contactInformationRepository.save(contactInformation);
        vacancy.setContactInformation(contactInformation);
        contactInformation.setVacancy(vacancy);

        System.out.println(contactInformation.toString());
        salary.setVacancy(vacancy);
        vacancy.setSalary(salary);
        System.out.println(salary);
        vacancy.setEmployer(employer);
        employer.addVacancy(vacancy);
        vacancy.setEmployers_id(id);
        vacancyRepository.save(vacancy);

        return vacancyMapper.toDto(vacancy);
    }

    @Override
    public void delete(Long id) {
        vacancyRepository.deleteById(id);
    }

    @Override
    public List<Vacancy> getAll() {
        return vacancyRepository.findAll();
    }


    @Override
    public String getIdFromSecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = "";
        String id = "";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String s = principal.toString();
//            email = principal.toString().substring(46 + 7, 72 - 3);
            id = principal.toString().substring(5 + 3, 11 - 2);
        }
        return id;
    }

    @Override
    public VacancyResponse update(Long id, VacancyRequest vacancyRequest) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vacancy not found!"));
        update(vacancy, vacancyRequest);
        return vacancyMapper.toDto(vacancyRepository.save(vacancy));

    }

    @Override
    public List<JobSeekerVacanciesResponses> jobSeekerVacancies() {


        List<Vacancy> vacancies = vacancyRepository.findAll();
        return jobSeekerMapper.convertToVacancyJobSeekerResponses(vacancies);
    }
    @Override
    public List<VacancyResponse> getMyVacancies(Long id) {
        return vacancyMapper.toDtos(vacancyRepository.findAllByEmployerId(id));
    }



    public void update(Vacancy vacancy, VacancyRequest vacancyRequest) {
        if (vacancyRequest.getName() != null) {
            vacancy.setName(vacancyRequest.getName());
        }
        if (vacancyRequest.getSkills() != null) {
            vacancy.setSkills(vacancyRequest.getSkills());
        }
        if (vacancyRequest.getDescription() != null) {
            vacancy.setDescription(vacancyRequest.getDescription());
        }
        if (vacancyRequest.getDate() != null) {
            vacancy.setDate(vacancyRequest.getDate());
        }
        if (vacancyRequest.getPositionRequest() != null) {
            vacancy.setPosition(positionService.convertToEntity(vacancyRequest.getPositionRequest()));
        }
        if (vacancyRequest.getContactInfo() != null) {
            vacancy.setContactInfo(vacancyRequest.getContactInfo());
        }
        if (vacancyRequest.getSalaryRequest() != null) {
            vacancy.setSalary(salaryService.convertToEntity(vacancyRequest.getSalaryRequest()));
        }
    }
    @Override
    public List<JobSeekerVacanciesResponses> searchVacancy(String search) {
        String upper = search.toUpperCase();
        List<Vacancy> all = vacancyRepository.findAll();
        List<JobSeekerVacanciesResponses> jobSeekerVacanciesResponses = new ArrayList<>();
        for (Vacancy v : all) {
            if (v.getName().toUpperCase().contains(upper) || v.getDescription().toUpperCase().contains(upper) || v.getSkills().toUpperCase().contains(upper) ||
                    v.getContactInfo().toUpperCase().contains(upper) || v.getPosition().getName().toUpperCase().contains(upper) || v.getPosition().getCategory().getName().toUpperCase().contains(upper) ||
                    v.getEmployer().getCompanyName().toUpperCase().contains(upper) || v.getEmployer().getCity().toUpperCase().contains(upper) || v.getEmployer().getCountry().toUpperCase().contains(upper) ||
                    v.getEmployer().getAddress().toUpperCase().contains(upper)) {
                jobSeekerVacanciesResponses.add(jobSeekerVacanciesResponsesMapper.toDtos(v));
            }
        }
        return jobSeekerVacanciesResponses;
    }
//    public List<JobSeekerVacanciesResponses>filterInMyVacancies(Integer responses, Date date, StatusOfVacancy statusOfVacancy){
//
//    }

    @Override
    public List<JobSeekerVacanciesResponses> filter(String category, String position, String country, String city, Experience experience) {
        List<Vacancy> all = vacancyRepository.findAll();
        Iterator<Vacancy> iterator = all.iterator();

        while (iterator.hasNext()) {
            Vacancy v = iterator.next();
            if (category != null && !v.getPosition().getCategory().getName().contains(category)) {
                iterator.remove();
            } else if (position != null && !v.getPosition().getName().contains(position)) {
                iterator.remove();
            } else if (country != null && !v.getEmployer().getCountry().contains(country)) {
                iterator.remove();
            } else if (city != null && !v.getEmployer().getCity().contains(city)) {
                iterator.remove();
            }
        }

        return jobSeekerMapper.convertToVacancyJobSeekerResponses(all);
    }

    @Override
    public VacancyResponse updateById(Long id, VacancyRequest vacancyRequest) {
        VacancyResponse vacancyResponse =
                vacancyMapper.toDto(vacancyRepository.findById(id).orElseThrow());
        return vacancyResponse;
    }

    @Override
    public VacancyResponse updateEmployerVacancyByIds(Long employerId, Long vacancyId, VacancyRequest vacancyRequest) {
        Employer employer = employeeRepository.findById(employerId).orElseThrow();
        Vacancy vacancy =  employer.getVacancyList().get(Math.toIntExact(vacancyId));
        //vacancyRequest = vacancyMapper.toDto(vacancy);
        return vacancyMapper.requestToResponse( vacancyRequest);
    }

    @Override
    public Response uploadImage(MultipartFile file, Long id) throws IOException {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(()-> new NotFoundException("employer not found!"));
        if (vacancy.getImage() != null) {
            ImageData image = vacancy.getImage();
            vacancy.setImage(null);
            ImageData save = storageService.uploadImage(file, image);
            vacancy.setImage(save);
            vacancyRepository.save(vacancy);
        } else {
            ImageData image = storageService.uploadImage(file);
            vacancy.setImage(image);
            vacancyRepository.save(vacancy);
        }

        return null;
    }

}
