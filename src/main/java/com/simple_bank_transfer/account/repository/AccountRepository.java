package com.simple_bank_transfer.account.repository;

import com.simple_bank_transfer.account.repository.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
