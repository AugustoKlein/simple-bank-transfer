package com.simple_bank_transfer.notification.listener;

import com.simple_bank_transfer.notification.dto.NotificationMessage;
import com.simple_bank_transfer.notification.mapper.NotificationMapper;
import com.simple_bank_transfer.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationListener {

    private NotificationService notificationService;
    
    @RabbitListener(queues = "${spring.custom-config.rabbitmq.queue}")
    public void receive(NotificationMessage notificationMessage) {
        notificationService.create(NotificationMapper.toNotificationDto(notificationMessage));
    }

}
