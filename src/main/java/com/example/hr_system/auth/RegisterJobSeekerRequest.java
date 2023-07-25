package com.example.hr_system.auth;

import com.example.hr_system.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterJobSeekerRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
}
