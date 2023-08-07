package com.example.hr_system.dto.position;

import com.example.hr_system.dto.category.CategoryResponse;
import com.example.hr_system.dto.experience.ExperienceResponse;
import lombok.*;

@Data
public class PositionResponse {

    private Long id;

    private String name;

    private CategoryResponse categoryResponse;

    private ExperienceResponse experience;


}
