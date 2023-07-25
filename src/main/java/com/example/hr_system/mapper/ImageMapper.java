package com.example.hr_system.mapper;

import com.example.hr_system.dto.image.Response;
import com.example.hr_system.entities.ImageData;
import com.example.hr_system.repository.StorageRepository;

import java.util.List;

public interface ImageMapper {
    Response toDto(ImageData image);

    List<Response> toDtos(List<ImageData> image);
}
