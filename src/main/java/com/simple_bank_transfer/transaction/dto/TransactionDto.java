package com.simple_bank_transfer.transaction.dto;

import lombok.Builder;

@Builder
public record TransactionDto(Long id,
                             String message,
                             Long accountSenderId,
                             Long accountReceiverId,
                             Long amountTransferred) {
}
