package com.example.hr_system.service;

import com.example.hr_system.entities.FileData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileDataService {
    FileData uploadFile(MultipartFile file, FileData pdf) throws IOException;

    FileData uploadFile(MultipartFile file) throws IOException;

    ResponseEntity<?> downloadFile(Long id);
}
