package com.example.hr_system.dto.image;


import lombok.Data;

@Data
public class Response {

    Long id;
    String name;
    String type;
    byte[] imageData;
    Long jobSeekerId;


}
