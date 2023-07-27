package com.example.hr_system.service.impl;

import com.example.hr_system.entities.FileData;
import com.example.hr_system.repository.FileRepository;
import com.example.hr_system.service.FileDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileDataServiceImpl implements FileDataService {

    private final FileRepository repository;
    @Override
    public FileData uploadFile(MultipartFile file, FileData pdf) throws IOException {

        if (pdf != null) {
            repository.delete(pdf);
        }

        FileData fileData = repository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData(file.getBytes())
                .build());

        repository.save(fileData);
        System.out.println(fileData.getId()+"lddl\n\n\n");
        return fileData;
    }

    @Override
    public FileData uploadFile(MultipartFile file) throws IOException {

        FileData fileData = repository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData(file.getBytes())
                .build());
        System.out.println(fileData.getId()+"lddl2nd\n\n\n");


        return repository.save(fileData);
    }



    @Override
    public ResponseEntity<?> downloadFile(Long id) {
        Optional<FileData> dbFileData = repository.findById(id);
        if (dbFileData.isPresent()) {
            byte[] imageData = dbFileData.get().getFileData();
            String contentType = dbFileData.get().getType();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
