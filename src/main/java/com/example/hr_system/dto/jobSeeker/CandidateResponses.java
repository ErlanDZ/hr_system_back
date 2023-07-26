package com.example.hr_system.dto.jobSeeker;

import com.example.hr_system.dto.experience.Response;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.entities.Position;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;


@Data
public class CandidateResponses {

    Long isFavorite;
    Long imageId;
    String firstname;
    String lastname;
    List<String> experience;
    String country;
    String city;

}
