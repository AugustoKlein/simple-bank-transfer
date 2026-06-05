package com.simple_bank_transfer.account.service;

import com.simple_bank_transfer.account.dto.AccountDto;

public interface AccountService {
    Long create(AccountDto accountDto);

    AccountDto find(Long id);

    void increaseBalance(Long id, Long amount);

    void decreaseBalance(Long id, Long amount);

    boolean exists(Long id);
}
