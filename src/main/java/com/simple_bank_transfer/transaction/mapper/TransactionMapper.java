package com.simple_bank_transfer.transaction.mapper;

import com.simple_bank_transfer.transaction.dto.TransactionDto;
import com.simple_bank_transfer.transaction.dto.TransactionRequest;
import com.simple_bank_transfer.transaction.repository.entity.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionMapper {

    public static TransactionDto toTransactionDto(TransactionRequest transactionRequest) {
        return TransactionDto.builder()
                .accountReceiverId(transactionRequest.accountReceiverId())
                .accountSenderId(transactionRequest.accountSenderId())
                .amountTransferred(transactionRequest.amountTransferred())
                .build();
    }

    public static Transaction toTransaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .accountReceiverId(transactionDto.accountReceiverId())
                .accountSenderId(transactionDto.accountSenderId())
                .amountTransferred(transactionDto.amountTransferred())
                .build();
    }

    public static TransactionDto toTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .accountReceiverId(transaction.getAccountReceiverId())
                .accountSenderId(transaction.getAccountSenderId())
                .amountTransferred(transaction.getAmountTransferred())
                .build();
    }
}
