package com.simple_bank_transfer.transaction.controller;

import com.simple_bank_transfer.transaction.dto.TransactionDto;
import com.simple_bank_transfer.transaction.dto.TransactionRequest;
import com.simple_bank_transfer.transaction.mapper.TransactionMapper;
import com.simple_bank_transfer.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<TransactionDto>> getAll(@PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(transactionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getAll(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.find(id));
    }
}
