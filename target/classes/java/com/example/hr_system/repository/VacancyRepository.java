package com.example.hr_system.repository;

import com.example.hr_system.dto.EmployeeVacanciesResponses;
import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy,Long> {
  //  @Query(value = "select * from vacancies where id = 1", nativeQuery = true)
//  @Query(
//          value = "SELECT * FROM VACANCIES u WHERE u.employer_id = id",
//          nativeQuery = true)
//    List<Vacancy> getByIdEmployerVacancy(Long id);

  List<Vacancy> findAllByEmployerId(Long id);



    //List<Vacancy> findByEmployersId(Long id);

    @Query("select new com.example.hr_system.dto.JobSeekerVacanciesResponses(v.id) from Vacancy v")
    List<JobSeekerVacanciesResponses> jobSeekerVacanciesResponses();
//    @Query("select new com.example.hr_system.dto.EmployeeVacanciesResponses(v.id,v.date) from Vacancy v")

//    @Query(value = "select * from vacancies where employer_id = id", nativeQuery = true)
//    List<Vacancy> employeeVacancies(Long id);

    @Query("select new com.example.hr_system.dto.JobSeekerVacanciesResponses(v.id)from Vacancy v where v.position.name like upper(concat('%',:position, '%')) and " +
            "v.position.category.name like upper(concat('%',:category, '%')) and v.employer.country like upper(concat('%',:country, '%')) and " +
            "v.employer.city like upper(concat('%',:city, '%'))")
    List<JobSeekerVacanciesResponses> filter(String position, String category, String country, String city);

//    @Query("select new com.example.hr_system.dto.EmployeeVacanciesResponses(v.id,v.date) from Vacancy v")
//    List<EmployeeVacanciesResponses> employeeVacancies(Long id);


}
