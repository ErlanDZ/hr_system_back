package com.example.hr_system.auth;

import com.example.hr_system.auth.responses.UserWrapper;
import com.example.hr_system.dto.UserResponse;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private UserResponse user;
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
}
