package com.example.hr_system.auth;

import com.example.hr_system.auth.responses.AuthenticationEmployerResponse;
import com.example.hr_system.auth.responses.AuthenticationJobSeekerResponse;
import com.example.hr_system.auth.responses.UserWrapper;
import com.example.hr_system.config.JwtService;
import com.example.hr_system.dto.jobSeeker.JobSeekerRequest;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.dto.UserResponse;
import com.example.hr_system.entities.JobSeeker;
import com.example.hr_system.enums.Role;
import com.example.hr_system.mapper.JobSeekerMapper;
import com.example.hr_system.repository.EmployerRepository;
import com.example.hr_system.service.EmployerService;
import com.example.hr_system.service.JobSeekerService;
import com.example.hr_system.token.Token;
import com.example.hr_system.token.TokenRepository;
import com.example.hr_system.token.TokenType;
import com.example.hr_system.entities.User;
import com.example.hr_system.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import com.example.hr_system.config.SecurityConfiguration.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JobSeekerMapper jobSeekerMapper;
    private final AuthenticationManager authenticationManager;
    private final JobSeekerService jobSeekerService;
    private final EmployerService employerService;
    private final EmployerRepository employerRepository;


   // public AuthenticationResponse googleAccess(GoogleAccess request) {}

        public AuthenticationResponse adminRegister(RegisterJobSeekerRequest request) {
        if (repository.findByEmail(request.getEmail()).stream().count()>0) {
             throw new RuntimeException(request.getEmail() + " is already exists");
        }
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
//
//        Employer employer = new Employer();
//        employer.setCompanyName(user.getFirstname());
//        employer.setEmail(user.getEmail());
//        employer.setPassword(user.getPassword());
//        employerRepository.save(employer);
//
//        user.setEmployer(employer);
        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

 //   private OAuth2AuthorizedClientService clientService;

    public AuthenticationResponse employerRegister(RegisterEmployerRequest request) {
        if (repository.findByEmail(request.getEmail()).stream().count()>0) {
            throw new RuntimeException(request.getEmail() + " is already exists");
        }
        User user = new User();
        user.setFirstname(request.getCompanyName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYER);

        Employer employer = new Employer();
        employer.setCompanyName(user.getFirstname());
        employer.setEmail(user.getEmail());
        employer.setPassword(user.getPassword());
        employerRepository.save(employer);

        user.setEmployer(employer);
        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().build();
    }
    public void validationRegister(String email, String password){
            if (!email.contains("@")){
                throw new BadCredentialsException("invalid email!");
            }
            if (password.length()<8){
                throw new BadCredentialsException("invalid password!");
            }
    }

    public AuthenticationResponse jobSeekerRegister(RegisterJobSeekerRequest request) {
        if (repository.findByEmail(request.getEmail()).stream().count()>0) {
            throw new RuntimeException(request.getEmail() + " is already exists");
        }

//    JobSeeker jobSeeker=new JobSeeker();
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setEmail(request.getEmail());
        jobSeeker.setFirstname(request.getFirstname());
        jobSeeker.setLastname(request.getLastname());
        jobSeeker.setPassword(passwordEncoder.encode(request.getPassword()));
        jobSeeker.setRole(Role.JOB_SEEKER);
       // jobSeekerService.save(jobSeekerRequest);
        var user = User.builder()
                .jobSeeker(jobSeeker)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.JOB_SEEKER)
                .build();

//    user.setJobSeeker(jobSeeker);


        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("1here\n\n\n");


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            // Обработка ошибки аутентификации, например, неверный email или пароль
            throw new BadCredentialsException("Authentication failed: " + e.getMessage() + request.getEmail());
        }
        System.out.println("2here\n\n\n");
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        System.out.println("3here\n\n\n" + user.toString() + jwtToken + " ---- " + refreshToken);
//        UserWrapper userWrapper = (
//                user.getRole().equals(Role.ADMIN)?new UserWrapper(user):
//                        user.getRole().equals(Role.EMPLOYER)?new UserWrapper(user.getEmployer()):
//                                new UserWrapper(user.getJobSeeker()));

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        System.out.println("4here\n\n\n");


        return AuthenticationResponse.builder()
                .user(convertToresponse(user))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private UserResponse convertToresponse(User user) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setFirstname(user.getFirstname());
            userResponse.setLastname(user.getLastname());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            return userResponse;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
