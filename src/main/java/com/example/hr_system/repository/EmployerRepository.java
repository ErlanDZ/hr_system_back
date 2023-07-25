package com.example.hr_system.repository;


import com.example.hr_system.dto.jobSeeker.JobSeekerResponses;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
