package com.example.hr_system.mapper;

import com.example.hr_system.dto.position.PositionRequest;
import com.example.hr_system.dto.position.PositionResponse;
import com.example.hr_system.entities.Position;

import java.util.List;

public interface PositionMapper {

    PositionResponse toDto(Position position);
    List<PositionResponse> toDto(List<Position> position);

    PositionResponse requestToResponse(PositionRequest positionRequest);
}
