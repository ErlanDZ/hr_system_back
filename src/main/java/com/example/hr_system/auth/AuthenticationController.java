package com.example.hr_system.auth;

import com.example.hr_system.config.JwtService;
import com.example.hr_system.entities.User;
import com.example.hr_system.repository.UserRepository;
import com.example.hr_system.service.emailSender.EmailSenderService;
import com.example.hr_system.token.Token;
import com.example.hr_system.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {
//{
//  "sub": "baktybek@gmail.com",
//  "iat": 1689738537,
//  "exp": 1689824937
//}

    //job_seeker: name, position,opt rabotty, location
    private final AuthenticationService service;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtUtils;
    int randomNumber = 0;

    @GetMapping("/google")
    ResponseEntity<AuthenticationResponse> googleAccess(@RequestParam String authorizationHeader, String role) {
        Token token = new Token(
                1L,authorizationHeader, TokenType.BEARER,true,true, new User());
        System.out.println(jwtUtils.extractUsername(authorizationHeader));

//        Authentication authentication = new Authentication(token);
//        String token = authorizationHeader.replace("Bearer ", "");
//        String email = jwtUtils.extractEmail(token);
        return null;
    }

    @PostMapping("/register/job")
    public ResponseEntity<AuthenticationResponse> jobSeekerRegister(@RequestBody RegisterJobSeekerRequest request) {
        return ResponseEntity.ok(service.jobSeekerRegister(request));
    }
    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> adminRegister(@RequestBody RegisterJobSeekerRequest request) {
        return ResponseEntity.ok(service.adminRegister(request));
    }
    @PostMapping("/register/emp")
    public ResponseEntity<AuthenticationResponse> employerRegister(@RequestBody RegisterEmployerRequest request) {
        return ResponseEntity.ok(service.employerRegister(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        //User user  = userRepository.findByEmail(request.getEmail()).orElseThrow();
       // System.out.println(user.toString());
//        if(userRepository.findByEmail(request.getEmail())==null){
//            throw new NotFoundException(request.getEmail()+" not found!\n\n\n");
//        }
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
    @PostMapping("email_sender/send")
    public boolean sender(@RequestParam String email){
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        System.out.println(email);

        Random random = new Random();
        randomNumber = random.nextInt(max - min + 1) + min;
        if( userRepository.findByEmail(email)==null){
            throw new NotFoundException(email+" is not registered!(not found)");
        }
        emailSenderService.sendEmail(email,"Refresh your account","Please, write the code to site!",randomNumber);
        return true;
    }

    @PostMapping("/email_sender/check")
    public boolean checking_code(@RequestParam int code){
        return randomNumber==code;
    }

    @PostMapping("/email_sender/changePassword")
    public boolean changePassword(@RequestParam String email, @RequestParam String password){
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }
}
