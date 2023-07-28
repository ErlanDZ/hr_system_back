package com.example.hr_system.controller;



import com.example.hr_system.dto.JobSeekerVacanciesResponses;
import com.example.hr_system.dto.vacancy.VacancyRequest;
import com.example.hr_system.dto.vacancy.VacancyResponse;
import com.example.hr_system.entities.Experience;
import com.example.hr_system.service.StorageService;
import com.example.hr_system.service.impl.VacancyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@PreAuthorize("hasAnyAuthority('ADMIN')")
public class VacancyController {

    private final VacancyServiceImpl vacancyService;
    private final StorageService service;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long id) throws IOException {


        return ResponseEntity.status(HttpStatus.OK)
                .body(vacancyService.uploadImage(file,id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Long id){
        System.out.println("asghjd");
        return service.downloadImage(id);
    }



//    @PostMapping("/{employeeId}")
//    public VacancyResponse save(@PathVariable Long employeeId,
//                                @RequestBody VacancyRequest vacancyRequest){
//        return vacancyService.saveVacancy(employeeId,vacancyRequest);
//    }




}



