package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.image.Response;

import com.example.hr_system.entities.ImageData;
import com.example.hr_system.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageMapperImpl implements ImageMapper {
    @Override
    public Response toDto(ImageData image) {
        if (image==null){
            return null;
        }
        Response response=new Response();
        response.setId(image.getId());
        response.setName(image.getName());
        response.setType(image.getType());

        return response;
    }

    @Override
    public List<Response> toDtos(List<ImageData> images) {
        List<Response> responses=new ArrayList<>();
        for (ImageData image:images) {
            responses.add(toDto(image));
        }
        return responses;
    }
}
