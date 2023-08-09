package com.example.hr_system.service.emailSender;

import com.example.hr_system.entities.FileData;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDataInputStreamSourceAdapter implements InputStreamSource {

    private final FileData fileData;

    public FileDataInputStreamSourceAdapter(FileData fileData) {
        this.fileData = fileData;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileData.getFileData()); // Assuming getData() returns byte[] data
    }
}

