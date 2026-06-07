package com.simple_bank_transfer.account.service;

import com.simple_bank_transfer.account.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Long create(AccountDto accountDto);

    AccountDto find(Long id);

    void increaseBalance(Long id, Long amount);

    void decreaseBalance(Long id, Long amount);

    Boolean exists(Long id);

    Page<AccountDto> findAll(Pageable pageable);
}
