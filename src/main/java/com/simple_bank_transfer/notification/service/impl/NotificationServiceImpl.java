package com.simple_bank_transfer.notification.service.impl;

import com.simple_bank_transfer.infra.configuration.RabbitMQConfiguration;
import com.simple_bank_transfer.notification.dto.NotificationDto;
import com.simple_bank_transfer.notification.dto.NotificationMessage;
import com.simple_bank_transfer.notification.mapper.NotificationMapper;
import com.simple_bank_transfer.notification.repository.NotificationRepository;
import com.simple_bank_transfer.notification.repository.entity.Notification;
import com.simple_bank_transfer.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfiguration rabbitMQConfiguration;

    private final NotificationRepository notificationRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void send(NotificationMessage notificationMessage) {
        rabbitTemplate.convertAndSend(
                rabbitMQConfiguration.getExchange(),
                rabbitMQConfiguration.getRoutingKey(),
                notificationMessage);
    }

    @Override
    public Long create(NotificationDto notificationDto) {
        Notification notification = notificationRepository.save(NotificationMapper.toNotification(notificationDto));

        return notification.getId();
    }


}
