package com.simple_bank_transfer.account.controller;

import com.simple_bank_transfer.account.dto.AccountDto;
import com.simple_bank_transfer.account.dto.AccountRequest;
import com.simple_bank_transfer.account.mapper.AccountMapper;
import com.simple_bank_transfer.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AccountRequest accountRequest) {
        Long id = accountService.create(AccountMapper.toAccountDto(accountRequest));
        return ResponseEntity.created(URI.create(String.format("/account/%s", id))).build();
    }

    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(accountService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAll(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.find(id));
    }
}
