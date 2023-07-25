package com.example.hr_system.dto;


import com.example.hr_system.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}
