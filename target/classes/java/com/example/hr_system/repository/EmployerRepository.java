package com.example.hr_system.repository;


import com.example.hr_system.entities.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
