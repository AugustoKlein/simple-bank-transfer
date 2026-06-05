package com.simple_bank_transfer.transaction.service;

import com.simple_bank_transfer.transaction.dto.TransactionDto;

public interface TransactionService {

    Long create(TransactionDto transactionDto);
}
