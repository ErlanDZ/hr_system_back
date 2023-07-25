package com.example.hr_system.dto.position;

import com.example.hr_system.dto.category.CategoryResponse;
import com.example.hr_system.dto.experience.Response;
import com.example.hr_system.entities.Experience;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
public class PositionResponse {

    private Long id;

    private String name;

    private CategoryResponse categoryResponse;

    private Response experience;


}
