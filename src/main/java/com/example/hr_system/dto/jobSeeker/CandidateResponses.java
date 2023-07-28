package com.example.hr_system.dto.jobSeeker;

import lombok.Data;


@Data
public class CandidateResponses {
    Long candidateId;
    Long isFavorite;
    Long imageId;
    String firstname;
    String lastname;
    String position;
    String experience;
    String country;
    String city;

}
