package com.example.hr_system.repository;


import com.example.hr_system.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {
    Experience findByName(String name);

}
