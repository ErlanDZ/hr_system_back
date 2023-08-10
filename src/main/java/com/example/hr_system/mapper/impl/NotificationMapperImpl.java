package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.notification.NotificationResponse;
import com.example.hr_system.entities.Notification;
import com.example.hr_system.mapper.NotificationMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationMapperImpl implements NotificationMapper {
    @Override
    public NotificationResponse toDto(Notification notification) {
        if (notification == null){
            return null;
        }

        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setUserId(
                notification.getUser()!=null?
                notification.getUser().getId():null);
        response.setContent(notification.getContent());
        return response;
    }

    @Override
    public List<NotificationResponse> toDtos(List<Notification> responseList) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification :responseList) {
            notificationResponses.add(toDto(notification));
        }
        return notificationResponses;
    }
}
