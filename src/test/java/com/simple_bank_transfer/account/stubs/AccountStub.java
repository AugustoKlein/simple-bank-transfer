package com.simple_bank_transfer.account.stubs;

import com.simple_bank_transfer.account.dto.AccountDto;
import com.simple_bank_transfer.account.dto.AccountRequest;
import com.simple_bank_transfer.account.repository.entity.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountStub {

    public static Account account() {
        return Account.builder()
                .id(1L)
                .name("John")
                .balance(100L)
                .build();
    }

    public static AccountDto accountDto() {
        return AccountDto.builder()
                .id(1L)
                .name("John")
                .balance(100L)
                .build();
    }
}
