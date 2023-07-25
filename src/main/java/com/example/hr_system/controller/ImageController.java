package com.example.hr_system.controller;


import com.example.hr_system.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*")
public class ImageController {

//    @Autowired
//    private StorageService service;
//
//    @PostMapping("/upload/{id}")
//    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long job_seekerId) throws IOException {
//        String uploadImage = service.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadImage);
//    }
//
//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
////        byte[] imageData=service.downloadImage(fileName);
////        return ResponseEntity.status(HttpStatus.OK)
////                .contentType(MediaType.valueOf("image/png"))
////                .body(imageData);
//        return service.downloadImage(fileName);
//    }
}
