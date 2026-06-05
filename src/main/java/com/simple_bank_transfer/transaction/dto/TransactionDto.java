package com.simple_bank_transfer.transaction.dto;

import lombok.Builder;

@Builder
public record TransactionDto(Long id,
                             Long accountSenderId,
                             Long accountReceiverId,
                             Long amountTransferred) {
}
