package com.simple_bank_transfer.notification.stubs;

import com.simple_bank_transfer.notification.dto.NotificationDto;
import com.simple_bank_transfer.notification.dto.NotificationMessage;
import com.simple_bank_transfer.notification.repository.entity.Notification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotificationStub {

    public static Notification notification() {
        return Notification.builder()
                .id(1L)
                .message("Template message")
                .accountId(1L)
                .build();
    }

    public static NotificationMessage notificationMessage() {
        return NotificationMessage.builder()
                .message("Template message")
                .accountId(1L)
                .build();
    }

    public static NotificationDto notificationDto() {
        return NotificationDto.builder()
                .id(1L)
                .message("Template message")
                .accountId(1L)
                .build();
    }
}
