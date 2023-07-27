package com.example.hr_system.dto.file;


import lombok.Data;

@Data
public class FileResponse {

    Long id;
    String name;
    String type;
    byte[] fileData;
    Long jobSeekerId;


}
