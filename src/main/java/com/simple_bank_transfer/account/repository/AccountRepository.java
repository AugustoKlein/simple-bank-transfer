package com.simple_bank_transfer.account.repository;

import com.simple_bank_transfer.account.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
