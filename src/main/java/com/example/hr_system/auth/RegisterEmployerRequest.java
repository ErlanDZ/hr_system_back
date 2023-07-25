package com.example.hr_system.auth;


import lombok.Data;

@Data
public class RegisterEmployerRequest {
    private String companyName;
    private String email;
    private String password;
}
