package com.simple_bank_transfer.notification.dto;

import lombok.Builder;

@Builder
public record NotificationDto(Long id, String message, Long accountId) {
}
