package com.simple_bank_transfer.transaction.dto;

import lombok.Builder;

@Builder
public record TransactionRequest(Long accountSenderId,
                                 Long accountReceiverId,
                                 Long amountTransferred) {
}
