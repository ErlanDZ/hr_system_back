package com.example.hr_system.service.impl;

import com.example.hr_system.entities.ImageData;
import com.example.hr_system.repository.StorageRepository;
import com.example.hr_system.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StorageServiceImp implements StorageService {

    private final StorageRepository repository;

    public ImageData uploadImage(MultipartFile file, ImageData image) throws IOException {

        if (image != null) {
            repository.delete(image);
        }

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(file.getBytes())
                .build());

        repository.save(imageData);
        return imageData;
    }

    @Override
    public ImageData uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(file.getBytes())
                .build());

        return repository.save(imageData);
    }

    public ResponseEntity<?> downloadImage(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        if (dbImageData.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(dbImageData.get().getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}