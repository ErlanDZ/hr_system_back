package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.position.PositionRequest;
import com.example.hr_system.dto.position.PositionResponse;
import com.example.hr_system.entities.Position;
import com.example.hr_system.mapper.CategoryMapper;
import com.example.hr_system.mapper.PositionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PositionMapperImpl implements PositionMapper {
    private CategoryMapper categoryMapper;

    @Override
    public PositionResponse toDto(Position position) {
        if (position == null) {
            return null;
        }
        PositionResponse response = new PositionResponse();
        response.setId(position.getId());
        response.setName(position.getName());
        if (position.getCategory()!=null) {
            response.setCategoryResponse(categoryMapper.toDto(position.getCategory()));
        }
        return response;
    }

    @Override
    public List<PositionResponse> toDto(List<Position> positions) {
        List<PositionResponse> positionResponses=new ArrayList<>();
        for (Position position:positions) {
            positionResponses.add(toDto(position));
        }
        return positionResponses;
    }

    @Override
    public PositionResponse requestToResponse(PositionRequest positionRequest) {
        PositionResponse positionResponse = new PositionResponse();
        positionResponse.setCategoryResponse(categoryMapper.requestToResponse(positionRequest.getCategoryRequest()));
        positionResponse.setName(positionRequest.getName());
        positionResponse.setExperience(positionRequest.getExperience());
        return positionResponse;
    }
}
