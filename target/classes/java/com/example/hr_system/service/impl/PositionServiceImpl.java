package com.example.hr_system.service.impl;

import com.example.hr_system.dto.category.CategoryRequest;
import com.example.hr_system.dto.position.PositionRequest;
import com.example.hr_system.dto.position.PositionResponse;
import com.example.hr_system.entities.Category;
import com.example.hr_system.entities.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl {

    private final CategoryServiceImpl categoryService;

    public Position convertToEntity(PositionRequest positionRequest) {
        if (positionRequest == null) {
            return null;
        }
        Position position = new Position();
        position.setName(positionRequest.getName());
        CategoryRequest categoryRequest = positionRequest.getCategoryRequest();
        Category category = new Category();
        category.setName(categoryRequest.getName());
        position.setCategory(category);
        return position;
    }

}
