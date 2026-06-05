package com.simple_bank_transfer.transaction.repository;

import com.simple_bank_transfer.transaction.repository.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
