package com.simple_bank_transfer.notification.service;

import com.simple_bank_transfer.infra.configuration.RabbitMQConfiguration;
import com.simple_bank_transfer.notification.enums.StatusEnum;
import com.simple_bank_transfer.notification.repository.NotificationRepository;
import com.simple_bank_transfer.notification.service.impl.NotificationServiceImpl;
import com.simple_bank_transfer.notification.stubs.NotificationStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Notification Service Test")
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    public static final String DEFAULT_ERROR_MESSAGE = "something went wrong";

    private static final String exchange = "notification-queue.ex";
    private static final String routingKey = "notification-queue.rk";

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitMQConfiguration rabbitMQConfiguration;

    @Mock
    private NotificationRepository notificationRepository;

    @Nested
    @DisplayName("Send")
    class Send {
        @Test
        @DisplayName("Should send notification message")
        void sendSuccess() {
            when(rabbitMQConfiguration.getExchange()).thenReturn(exchange);
            when(rabbitMQConfiguration.getRoutingKey()).thenReturn(routingKey);

            doNothing().when(rabbitTemplate).convertAndSend(exchange, routingKey, NotificationStub.notificationMessage());

            notificationService.send(NotificationStub.notificationMessage());

            verify(rabbitMQConfiguration).getExchange();
            verify(rabbitMQConfiguration).getRoutingKey();
            verify(rabbitTemplate).convertAndSend(exchange, routingKey, NotificationStub.notificationMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when rabbitTemplate fails")
        void sendFail() {
            when(rabbitMQConfiguration.getExchange()).thenReturn(exchange);
            when(rabbitMQConfiguration.getRoutingKey()).thenReturn(routingKey);

            doThrow(AmqpException.class).when(rabbitTemplate).convertAndSend(exchange, routingKey, NotificationStub.notificationMessage());

            assertThrows(IllegalArgumentException.class, () -> notificationService.send(NotificationStub.notificationMessage()));

            verify(rabbitMQConfiguration).getExchange();
            verify(rabbitMQConfiguration).getRoutingKey();
            verify(rabbitTemplate).convertAndSend(exchange, routingKey, NotificationStub.notificationMessage());
        }
    }

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("Should create notification")
        public void createSuccess() {
            when(notificationRepository.save(any())).thenReturn(NotificationStub.notification());

            Long id = notificationService.create(NotificationStub.notificationDto());

            assertEquals(1L, id);
            verify(notificationRepository).save(any());
        }

        @Test
        @DisplayName("Should not create when IllegalArgumentException is thrown")
        public void createFail() {
            when(notificationRepository.save(any())).thenThrow(new IllegalArgumentException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(IllegalArgumentException.class, () -> notificationService.create(NotificationStub.notificationDto())).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(notificationRepository).save(any());
        }
    }

    @Nested
    @DisplayName("CheckPendingNotifications")
    class CheckPendingNotifications {
        @Test
        @DisplayName("Should check PENDING notifications and send it exchange via rabbitmq")
        void checkPendingNotificationsSuccess() {
            when(notificationRepository.findAllByStatus(StatusEnum.PENDING)).thenReturn(List.of(NotificationStub.notification()));

            when(rabbitMQConfiguration.getExchange()).thenReturn(exchange);
            when(rabbitMQConfiguration.getRoutingKey()).thenReturn(routingKey);

            doNothing().when(rabbitTemplate).convertAndSend(exchange, routingKey, NotificationStub.notificationMessage());

            when(notificationRepository.save(any())).thenReturn(NotificationStub.notification());

            notificationService.checkPendingNotifications();

            verify(notificationRepository).findAllByStatus(StatusEnum.PENDING);
            verify(rabbitMQConfiguration).getExchange();
            verify(rabbitMQConfiguration).getRoutingKey();
            verify(rabbitTemplate).convertAndSend(exchange, routingKey, NotificationStub.notificationMessage());
            verify(notificationRepository).save(any());
        }
    }
}
