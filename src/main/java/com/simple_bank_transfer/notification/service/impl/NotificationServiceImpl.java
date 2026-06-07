package com.simple_bank_transfer.notification.service.impl;

import com.simple_bank_transfer.infra.configuration.RabbitMQConfiguration;
import com.simple_bank_transfer.infra.exception.BusinessException;
import com.simple_bank_transfer.notification.dto.NotificationDto;
import com.simple_bank_transfer.notification.dto.NotificationMessage;
import com.simple_bank_transfer.notification.enums.StatusEnum;
import com.simple_bank_transfer.notification.mapper.NotificationMapper;
import com.simple_bank_transfer.notification.repository.NotificationRepository;
import com.simple_bank_transfer.notification.repository.entity.Notification;
import com.simple_bank_transfer.notification.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfiguration rabbitMQConfiguration;

    private final NotificationRepository notificationRepository;

    @Override
    public void send(NotificationMessage notificationMessage) {
        try {
            rabbitTemplate.convertAndSend(
                    rabbitMQConfiguration.getExchange(),
                    rabbitMQConfiguration.getRoutingKey(),
                    notificationMessage);
        } catch (AmqpException exception) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Long create(NotificationDto notificationDto) {
        Notification notification = notificationRepository.save(NotificationMapper.toNotification(notificationDto));

        return notification.getId();
    }

    @Override
    @Transactional
    public void checkPendingNotifications() {
        List<Notification> pendingNotifications = notificationRepository.findAllByStatus(StatusEnum.PENDING);

        if (!pendingNotifications.isEmpty()) {
            for (Notification notification : pendingNotifications) {
                send(NotificationMapper.toNotificationMessage(notification));

                notification.setStatus(StatusEnum.SENT);

                notificationRepository.save(notification);
            }
        }
    }


}
