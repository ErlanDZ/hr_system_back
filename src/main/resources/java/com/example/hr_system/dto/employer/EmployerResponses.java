package com.example.hr_system.dto.employer;

import com.example.hr_system.dto.file.FileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerResponses {
    private Long id;
    private String companyName;
    private String aboutCompany;
    private String country;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private FileResponse fileResponse;
}
