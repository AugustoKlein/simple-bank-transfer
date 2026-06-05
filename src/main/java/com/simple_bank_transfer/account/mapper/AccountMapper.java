package com.simple_bank_transfer.account.mapper;

import com.simple_bank_transfer.account.dto.AccountDto;
import com.simple_bank_transfer.account.dto.AccountRequest;
import com.simple_bank_transfer.account.repository.entity.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {

    public static AccountDto toAccountDto(AccountRequest accountRequest) {
        return AccountDto.builder()
                .name(accountRequest.name())
                .balance(accountRequest.balance())
                .build();
    }

    public static Account toAccount(AccountDto accountRequest) {
        return Account.builder()
                .name(accountRequest.name())
                .balance(accountRequest.balance())
                .build();
    }

    public static AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .balance(account.getBalance())
                .build();
    }
}
