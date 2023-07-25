package com.example.hr_system.dto.contactInformation;

import lombok.Data;

@Data
public class ContactInformationRequest {
    private String country;
    private String city;
    private String street_house;
}
