package com.simple_bank_transfer.account.service;

import com.simple_bank_transfer.account.dto.AccountDto;

public interface AccountService {
    Long create(AccountDto accountDto);

    AccountDto find(Long id);
}
