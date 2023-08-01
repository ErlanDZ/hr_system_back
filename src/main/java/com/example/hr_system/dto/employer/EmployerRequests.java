package com.example.hr_system.dto.employer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmployerRequests {

    private String aboutCompany;
    private String companyName;
    private String country;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
}
