
package com.example.hr_system.service;

import com.example.hr_system.entities.ImageData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    ImageData uploadImage(MultipartFile file, ImageData image) throws IOException;
    ImageData uploadImage(MultipartFile file) throws IOException;
    ResponseEntity<?> downloadImage(Long id);
}