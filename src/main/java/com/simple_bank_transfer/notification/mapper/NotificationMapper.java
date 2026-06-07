package com.simple_bank_transfer.notification.mapper;

import com.simple_bank_transfer.notification.dto.NotificationDto;
import com.simple_bank_transfer.notification.dto.NotificationMessage;
import com.simple_bank_transfer.notification.repository.entity.Notification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotificationMapper {

    public static Notification toNotification(NotificationDto notificationDto) {
        return Notification.builder()
                .message(notificationDto.message())
                .accountId(notificationDto.accountId())
                .status(notificationDto.status())
                .build();
    }

    public static NotificationMessage toNotificationMessage(Notification notification) {
        return NotificationMessage.builder()
                .message(notification.getMessage())
                .accountId(notification.getAccountId())
                .build();
    }
}
