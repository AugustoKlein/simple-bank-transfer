package com.simple_bank_transfer.account.service.impl;

import com.simple_bank_transfer.account.dto.AccountDto;
import com.simple_bank_transfer.account.mapper.AccountMapper;
import com.simple_bank_transfer.account.repository.AccountRepository;
import com.simple_bank_transfer.account.repository.entity.Account;
import com.simple_bank_transfer.account.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Override
    public Long create(AccountDto accountDto) {
        Account account = accountRepository.save(AccountMapper.toAccount(accountDto));
        return account.getId();
    }

    @Override
    public AccountDto find(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return AccountMapper.toAccountDto(account);
    }

    @Override
    public void increaseBalance(Long id, Long amount) {
        Account account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Long newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        accountRepository.save(account);
    }

    @Override
    public void decreaseBalance(Long id, Long amount) {
        Account account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Long newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        accountRepository.save(account);
    }

    @Override
    public boolean exists(Long id) {
        return accountRepository.existsById(id);
    }

}
