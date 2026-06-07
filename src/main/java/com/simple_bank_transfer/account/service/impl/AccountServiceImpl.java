package com.simple_bank_transfer.account.service.impl;

import com.simple_bank_transfer.account.dto.AccountDto;
import com.simple_bank_transfer.account.mapper.AccountMapper;
import com.simple_bank_transfer.account.repository.AccountRepository;
import com.simple_bank_transfer.account.repository.entity.Account;
import com.simple_bank_transfer.account.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Boolean exists(Long id) {
        return accountRepository.existsById(id);
    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(AccountMapper::toAccountDto);
    }

}
