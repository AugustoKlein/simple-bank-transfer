package com.simple_bank_transfer.notification.scheduler;

import com.simple_bank_transfer.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationScheduler {

    private NotificationService notificationService;

    @Scheduled(fixedDelay = 5000)
    public void sendPendingNotifications() {
        notificationService.checkPendingNotifications();
    }
}
