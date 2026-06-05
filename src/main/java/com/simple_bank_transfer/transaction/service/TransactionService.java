package com.simple_bank_transfer.transaction.service;

import com.simple_bank_transfer.transaction.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Long create(TransactionDto transactionDto);

    Page<TransactionDto> findAll(Pageable pageable);

    TransactionDto find(Long id);
}
