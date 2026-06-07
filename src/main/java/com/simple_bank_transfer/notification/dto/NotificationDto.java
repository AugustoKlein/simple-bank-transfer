package com.simple_bank_transfer.notification.dto;

import com.simple_bank_transfer.notification.enums.StatusEnum;
import lombok.Builder;

@Builder
public record NotificationDto(Long id, String message, Long accountId, StatusEnum status) {
}
