package com.example.hr_system.repository;

import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.*;
import com.example.hr_system.enums.ApplicationDate;
import com.example.hr_system.enums.Education;
import com.example.hr_system.enums.StatusOfJobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
//@Query(value = "select vacancies from job_seeker_table where id=id", nativeQuery = true)
//List<Vacancy> findJobSeekerById(Long jobSeekerId);
//List<Vacancy> findJobSeekerByIdAndVacancies(Long jobSeekerId);

    List<JobSeeker> findByStatusOfJobSeekerAndExperienceAndUserApplicationDate(
            StatusOfJobSeeker statusOfJobSeeker, Experience experience, ApplicationDate applicationDate);



    @Query("SELECT j FROM JobSeeker j WHERE j.userApplicationDate >= ?1 AND j.userApplicationDate <= ?2")
    List<JobSeeker> findByUserApplicationDateRange(LocalDateTime start, LocalDateTime end);


    @Query("SELECT j FROM JobSeeker j WHERE " +
            "(:name IS NULL OR :name = '' OR CONCAT(j.firstname, ' ', j.lastname) LIKE %:name%)")
    List<JobSeeker> searchByName(@Param("name") String name);
@Query("SELECT js FROM JobSeeker js " +
        "WHERE (:position IS NULL OR js.position = :position) " +
        "AND (:education IS NULL OR js.education = :education) " +
        "AND (:country IS NULL OR js.country = :country) " +
        "AND (:city IS NULL OR js.city = :city) " +
        "AND (:experience IS NULL OR js.experience = :experience)")
List<JobSeeker> filterJobSeekers(
        Position position,
        Education education,
        String country,
        String city,
        Experience experience
);



    @Query("SELECT js FROM JobSeeker js " +
            "WHERE (:firstname IS NULL OR js.firstname = :firstname) " +
            "AND (:lastname IS NULL OR js.lastname = :lastname) " )
    List<JobSeeker> searchJobSeekers(
            String firstname,
            String lastname

    );


}
