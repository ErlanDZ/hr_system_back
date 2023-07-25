package com.example.hr_system.dto.jobSeeker;

import com.example.hr_system.dto.experience.Response;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.entities.Position;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
public class CandidateResponses {

    Long isFavorite;
    com.example.hr_system.dto.image.Response image;
    String firstname;
    String lastname;
    com.example.hr_system.dto.experience.Response experience;
    String country;
    String city;

}
