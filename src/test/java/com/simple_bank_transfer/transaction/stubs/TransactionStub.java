package com.simple_bank_transfer.transaction.stubs;

import com.simple_bank_transfer.transaction.dto.TransactionDto;
import com.simple_bank_transfer.transaction.dto.TransactionRequest;
import com.simple_bank_transfer.transaction.repository.entity.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionStub {

    public static Transaction transaction() {
        return Transaction.builder()
                .id(1L)
                .accountReceiverId(1L)
                .accountSenderId(2L)
                .accountReceiverId(10L)
                .build();
    }

    public static TransactionRequest transactionRequest() {
        return TransactionRequest.builder()
                .accountReceiverId(1L)
                .accountSenderId(2L)
                .accountReceiverId(10L)
                .build();
    }

    public static TransactionDto transactionDto() {
        return TransactionDto.builder()
                .id(1L)
                .accountReceiverId(1L)
                .accountSenderId(2L)
                .accountReceiverId(10L)
                .build();
    }
}
