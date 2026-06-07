package com.simple_bank_transfer.notification.service;

import com.simple_bank_transfer.notification.dto.NotificationDto;
import com.simple_bank_transfer.notification.dto.NotificationMessage;

public interface NotificationService {
    void send(NotificationMessage notificationMessage);

    Long create(NotificationDto notificationDto);

    void checkPendingNotifications();
}
