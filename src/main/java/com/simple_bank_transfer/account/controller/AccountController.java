package com.simple_bank_transfer.account.controller;

import com.simple_bank_transfer.account.dto.AccountRequest;
import com.simple_bank_transfer.account.mapper.AccountMapper;
import com.simple_bank_transfer.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
