package com.example.hr_system.dto.jobSeeker;


import com.example.hr_system.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSeekerRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

}
