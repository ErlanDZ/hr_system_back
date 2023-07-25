package com.example.hr_system.repository;

import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
//@Query(value = "select vacancies from job_seeker_table where id=id", nativeQuery = true)
//List<Vacancy> findJobSeekerById(Long jobSeekerId);
//List<Vacancy> findJobSeekerByIdAndVacancies(Long jobSeekerId);

}
