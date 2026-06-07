package com.simple_bank_transfer.notification.scheduler;

import com.simple_bank_transfer.notification.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@DisplayName("Notification Scheduler Test")
@ExtendWith(MockitoExtension.class)
public class NotificationSchedulerTest {

    @InjectMocks
    private NotificationScheduler notificationScheduler;

    @Mock
    private NotificationService notificationService;

    @Nested
    @DisplayName("SendPendingNotifications")
    class SendPendingNotifications {
        @Test
        @DisplayName("Should call the method checkPendingNotifications")
        void sendPendingNotificationsSuccess() {
            doNothing().when(notificationService).checkPendingNotifications();

            notificationScheduler.checkPendingNotifications();

            verify(notificationService).checkPendingNotifications();
        }
    }
}
