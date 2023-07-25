package com.example.hr_system.dto.position;

import com.example.hr_system.dto.category.CategoryRequest;
import com.example.hr_system.dto.experience.Response;
import com.example.hr_system.entities.Experience;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class PositionRequest {


    private String name;

    private CategoryRequest categoryRequest;

    private Response experience;

}
