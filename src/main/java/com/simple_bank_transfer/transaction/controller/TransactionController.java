package com.simple_bank_transfer.transaction.controller;

import com.simple_bank_transfer.transaction.dto.TransactionRequest;
import com.simple_bank_transfer.transaction.mapper.TransactionMapper;
import com.simple_bank_transfer.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TransactionRequest transactionRequest) {
        Long id = transactionService.create(TransactionMapper.toTransactionDto(transactionRequest));
        return ResponseEntity.created(URI.create(String.format("/transaction/%s", id))).build();
    }
}
