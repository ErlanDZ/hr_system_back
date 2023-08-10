package com.example.hr_system.service.impl;

import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.image.Response;
import com.example.hr_system.dto.jobSeeker.RespondedResponse;
import com.example.hr_system.dto.notification.NotificationResponse;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.*;
import com.example.hr_system.enums.ApplicationDate;
import com.example.hr_system.enums.StatusOfJobSeeker;
import com.example.hr_system.enums.StatusOfVacancy;
import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.mapper.*;
import com.example.hr_system.repository.*;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.FileDataService;
import com.example.hr_system.service.VacancyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final UserRepository userRepository;
    private final EmployerRepository employeeRepository;
    private final FileDataService fileDataService;
    private final VacancyRepository vacancyRepository;
    private final SalaryRepository salaryRepository;
    private final PositionRepository positionRepository;
    private final SalaryMapper salaryMapper;
    private final JobSeekerRepository jobSeekerRepository;
    private final VacancyMapper vacancyMapper;
    private final JobSeekerMapper jobSeekerMapper;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final JobSeekerVacanciesResponsesMapper jobSeekerVacanciesResponsesMapper;
    private final ContactInformationServiceImpl contactInformationService;
    private final ContactInformationRepository contactInformationRepository;
    private final ExperienceRepository experienceRepository;
    private final EmployerService employerService;

    private void saveNotification(User user, String content) {
        Notification notification = new Notification();
        if (user != null) {
            notification.setUser(user);
            notification.setContent(content);
            notificationRepository.save(notification);
        }
    }

    @Override
    public VacancyResponse saveVacancy(Long id, VacancyRequest vacancyRequest) {
        Vacancy vacancy = new Vacancy();
//        if (vacancyRequest.getSalaryRequest() != null) {
//            vacancy.setSalary(salaryMapper.toEntity(vacancyRequest.getSalaryRequest()));
//        }
        vacancy.setSalary(vacancyRequest.getSalaryRequest() != null ? salaryMapper.toEntity(vacancyRequest.getSalaryRequest()) : null);
        vacancy.setAbout_company(vacancyRequest.getAbout_company());
        vacancy.setIndustry(vacancyRequest.getIndustry());
        vacancy.setExperience(vacancyRequest.getExperience());
        vacancy.setAdditionalInformation(vacancyRequest.getAdditionalInformation());
        vacancy.setTypeOfEmploymentS(
                employerService.containsTypeOfEmployment(vacancyRequest.getTypeOfEmploymentS()) ?
                        TypeOfEmployment.valueOf(vacancyRequest.getTypeOfEmploymentS()) : TypeOfEmployment.NEPOLNIY_RABOCHIY_DEYN);
        vacancy.setSkills(vacancyRequest.getSkills());
        vacancy.setDescription(vacancyRequest.getDescription());

        vacancy.setStatusOfVacancy(StatusOfVacancy.OPEN);

        Position position = positionRepository.findByName(vacancyRequest.getPosition());
        vacancy.setPosition(position);

        Salary salary = salaryMapper.toEntity(vacancyRequest.getSalaryRequest());
        salaryRepository.save(salary);
        vacancy.setSalary(salary);
        vacancy.setCreationDate(LocalDateTime.now());


        ContactInformation contactInformation = contactInformationService.convertToEntity(vacancyRequest.getContactInformationRequest());
        contactInformationRepository.save(contactInformation);
        vacancy.setContactInformation(contactInformation);
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Employer employer = user.getEmployer();
        vacancy.setEmployer(employer);
        List<Notification> notifications = new ArrayList<>();


        for (User userJobSeeker : userRepository.findAll()) {
            if (vacancy.getPosition() != null && userJobSeeker.getJobSeeker() != null && userJobSeeker.getJobSeeker().getPosition() != null) {
                if (vacancy.getPosition().getName().equals(userJobSeeker.getJobSeeker().getPosition().getName())) {
                    Notification notification = new Notification();
                    notification.setUser(userJobSeeker);
                    notification.setContent("Появилась новая вакансия, " +
                            "соответствующая вашим интересам. " +
                            "Проверьте подробности и отправьте отклик.");
                    notifications.add(notification);
                }
            }
        }
        notificationRepository.saveAll(notifications);

        return vacancyMapper.toDto(vacancyRepository.save(vacancy));
    }


    @Override
    public void delete(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(()->
                new NotFoundException("is always dropped or not, the id: "+id));
        vacancy.getJobSeekers().clear();
        vacancy.setEmployer(null);
        vacancy.setJobSeeker(null);
       // vacancyRepository.save(vacancy);
        vacancyRepository.delete(vacancy);
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
        vacancyRepository.save(vacancy);
        return vacancyMapper.toDto(vacancy);

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
        if (vacancyRequest.getAbout_company() != null) {
            vacancy.setAbout_company(vacancyRequest.getAbout_company());
        }
        if (vacancyRequest.getSkills() != null) {
            vacancy.setSkills(vacancyRequest.getSkills());
        }
        if (vacancyRequest.getDescription() != null) {
            vacancy.setDescription(vacancyRequest.getDescription());
        }
//        if (vacancyRequest.getDate() != null) {
//            vacancy.setDate(vacancyRequest.getDate());
//        }

        if (vacancyRequest.getPosition() != null) {
            vacancy.setPosition(positionRepository.findByName(vacancyRequest.getPosition()));
        }

        if (vacancyRequest.getSalaryRequest() != null) {
            vacancy.setSalary(salaryMapper.toEntity(vacancyRequest.getSalaryRequest()));
        }

    }

    @Override
    public List<JobSeekerVacanciesResponses> searchVacancy(String search) {
        String upper = search.toUpperCase();
        List<Vacancy> all = vacancyRepository.findAll();
        List<JobSeekerVacanciesResponses> jobSeekerVacanciesResponses = new ArrayList<>();
        for (Vacancy v : all) {
            if (v.getAbout_company().toUpperCase().contains(upper) || v.getDescription().toUpperCase().contains(upper) || v.getSkills().toUpperCase().contains(upper) ||
                    v.getPosition().getName().toUpperCase().contains(upper) || v.getPosition().getCategory().getName().toUpperCase().contains(upper) ||
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
    public List<JobSeekerVacanciesResponses> filter(String category, String position, String country, String city, String experience1) {
        List<Vacancy> all = vacancyRepository.findAll();
        Iterator<Vacancy> iterator = all.iterator();
        //Experience experience = experienceRepository.findByName(experience1);

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
        Vacancy vacancy = employer.getVacancyList().get(Math.toIntExact(vacancyId));
        //vacancyRequest = vacancyMapper.toDto(vacancy);
        return vacancyMapper.requestToResponse(vacancyRequest);
    }

    @Override
    public Response uploadImage(MultipartFile file, Long id) throws IOException {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new NotFoundException("employer not found!"));
        if (vacancy.getResume() != null) {
            FileData image = vacancy.getResume();
            vacancy.setResume(null);
            FileData save = fileDataService.uploadFile(file, image);
            vacancy.setResume(save);
            vacancyRepository.save(vacancy);
        } else {
            FileData image = fileDataService.uploadFile(file);
            vacancy.setResume(image);
            vacancyRepository.save(vacancy);
        }

        return null;
    }

    @Override
    public VacancyResponse responded(Long vacancyId, Long userId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: "+userId));
        JobSeeker jobSeeker = user.getJobSeeker();

        List<JobSeeker> jobSeekers = new ArrayList<>();
        if (!vacancy.getJobSeekers().contains(jobSeeker)) {


            if (vacancy.getJobSeekers().isEmpty()) {

                jobSeekers.add(jobSeeker);
                vacancy.setJobSeekers(jobSeekers);
                vacancy.setResponse(vacancy.getJobSeekers().size());
                vacancyRepository.save(vacancy);
            } else {
                for (JobSeeker jobSeeker1 : vacancy.getJobSeekers()) {
                    if (!Objects.equals(jobSeeker1.getId(), userId)) {
                        vacancy.getJobSeekers().add(jobSeeker);
                        vacancy.setResponse(vacancy.getJobSeekers().size());
                        vacancyRepository.save(vacancy);
                    }
                    break;
                }
            }

            Notification notification = new Notification();
            User userEmployer = vacancy.getEmployer().getUser();
            if (userEmployer != null) {
                notification.setUser(userEmployer);
                notification.setContent("Job Seeker " + jobSeeker.getFirstname() +
                        " responded your vacancy " + vacancy.getPosition());
                notificationRepository.save(notification);
            }

        }
        return vacancyMapper.toDto(vacancy);
    }

    @Override
    public void setStatusOfJobSeeker(Long vacancyId, Long jobSeekerId, String status) {

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found!" + vacancyId));
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId).orElseThrow(() -> new NotFoundException("Job Seeker not found!"));

        if (vacancy.getJobSeekers().contains(jobSeeker)) {
            jobSeeker.setStatusOfJobSeeker(
                    employerService.containsStatusOfJobSeeker(status) ?
                            StatusOfJobSeeker.valueOf(status) : null);
            jobSeeker.setUserApplicationDate(LocalDateTime.now());
            jobSeekerRepository.save(jobSeeker);

            Notification notification = new Notification();
            notification.setUser(jobSeeker.getUser());
            if (jobSeeker.getStatusOfJobSeeker().equals(StatusOfJobSeeker.AN_INTERVIEW)) {
                notification.setContent("Вас пригласили на собеседование в " +
                        vacancy.getEmployer().getCompanyName() + " Подготовьтесь к встрече и ознакомьтесь с деталями.");

            } else if (jobSeeker.getStatusOfJobSeeker().equals(StatusOfJobSeeker.CONSIDERED)) {
                notification.setContent("Вы в расмотрении");
            } else if (jobSeeker.getStatusOfJobSeeker().equals(StatusOfJobSeeker.OFFER)) {
                notification.setContent("УРА вас приняли на работу");
            } else if (jobSeeker.getStatusOfJobSeeker().equals(StatusOfJobSeeker.REJECT)) {
                notification.setContent("Вас отклонили");
            }
            notificationRepository.save(notification);
        }
    }


    @Override
    public void setStatusOfVacancy(Long id, String statusOfVacancy) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vacancy not found!"));

        vacancy.setStatusOfVacancy(StatusOfVacancy.valueOf(statusOfVacancy));


        for (JobSeeker jobSeeker : vacancy.getJobSeekers()) {
            Notification notification = new Notification();
            if (jobSeeker.getUser() != null) {
                User user = jobSeeker.getUser();
                notification.setUser(user);
                notification.setContent("Company " + vacancy.getEmployer().getCompanyName() +
                        " change status vacancy of " + StatusOfVacancy.valueOf(statusOfVacancy));
                notificationRepository.save(notification);
            }
        }

        vacancyRepository.save(vacancy);
    }

    @Override
    public List<RespondedResponse> listForResponded(Long vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));
        return jobSeekerMapper.toDtosForListResponded(vacancy.getJobSeekers());
    }

    @Override
    public List<RespondedResponse> listForResponded(Long vacancyId, List<JobSeeker> jobSeekers) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(vacancyId);
        if (vacancyOptional.isEmpty()) {
            return null; // or handle the vacancy not found case as needed
        }
        List<JobSeeker> jobSeekers1 = (vacancyRepository.findById(vacancyId)).orElseThrow(() -> new EntityNotFoundException("Vacancy not found")).getJobSeekers();
        jobSeekers.retainAll(jobSeekers1);
        return jobSeekerMapper.toDtosForListResponded(jobSeekers);
    }

    @Override
    public List<RespondedResponse> listForResponded(
            Long vacancyId, String statusOfJobSeeker,
            String experience, String applicationDate) {
        StatusOfJobSeeker statusOfJobSeeker1 = StatusOfJobSeeker.valueOf(statusOfJobSeeker);
        Experience experience1 = experienceRepository.findByName(experience);

        LocalDate localDate = applicationDate.length() < 2 ? null :
                LocalDate.parse(applicationDate);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;
        LocalDateTime end;


        switch (applicationDate) {
            case "TODAY":
                start = now.with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;
            case "THIS_WEEK":
                start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
                end = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);
                break;
            case "THIS_MONTH":
                start = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                end = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
                break;
            case "THIS_YEAR":
                start = now.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
                end = now.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
                break;
            default:
                throw new IllegalArgumentException("Invalid time period");
        }
        List<JobSeeker> jobSeekers2 = jobSeekerRepository.findByUserApplicationDateRange(start, end);
        List<JobSeeker> jobSeekers = (vacancyRepository.findById(vacancyId)).orElseThrow(() -> new EntityNotFoundException("Vacancy not found")).getJobSeekers();


        List<JobSeeker> jobSeekers1 = jobSeekerRepository.findByStatusOfJobSeekerAndExperienceAndUserApplicationDate(
                statusOfJobSeeker1, experience1, null);

        jobSeekers1.retainAll(jobSeekers);
        return jobSeekerMapper.toDtosForListResponded(jobSeekers2);
    }
}
