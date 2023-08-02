package com.example.hr_system.controller;



import com.example.hr_system.service.impl.VacancyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@PreAuthorize("hasAnyAuthority('ADMIN')")
public class VacancyController {

    private final VacancyServiceImpl vacancyService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long id) throws IOException {


        return ResponseEntity.status(HttpStatus.OK)
                .body(vacancyService.uploadImage(file,id));
    }

}



