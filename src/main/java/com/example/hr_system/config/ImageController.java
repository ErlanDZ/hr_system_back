package com.example.hr_system.config;


import com.example.hr_system.service.JobSeekerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;

@RestController
@CrossOrigin(value = "*")
@AllArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final JobSeekerService jobSeekerService;


}
