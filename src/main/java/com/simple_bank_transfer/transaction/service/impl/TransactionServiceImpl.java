package com.simple_bank_transfer.transaction.service.impl;

import com.simple_bank_transfer.account.mapper.AccountMapper;
import com.simple_bank_transfer.account.repository.entity.Account;
import com.simple_bank_transfer.account.service.AccountService;
import com.simple_bank_transfer.infra.exception.BusinessException;
import com.simple_bank_transfer.notification.dto.NotificationMessage;
import com.simple_bank_transfer.notification.service.NotificationService;
import com.simple_bank_transfer.transaction.dto.TransactionDto;
import com.simple_bank_transfer.transaction.mapper.TransactionMapper;
import com.simple_bank_transfer.transaction.repository.TransactionRepository;
import com.simple_bank_transfer.transaction.repository.entity.Transaction;
import com.simple_bank_transfer.transaction.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private static final String NOTIFICATION_MESSAGE = "Account number: %s sent you %02d $";

    private static final String EXCEPTION_MESSAGE = "%s account number does not exist";

    private TransactionRepository transactionRepository;

    private NotificationService notificationService;

    private AccountService accountService;

    @Transactional
    @Override
    public Long create(TransactionDto transactionDto) {
        log.info("Create transaction");

        if (!accountService.exists(transactionDto.accountReceiverId())) {
            accountNotFoundException("receiver");
        }

        if (!accountService.exists(transactionDto.accountSenderId())) {
            accountNotFoundException("sender");
        }

        Transaction transaction = transactionRepository.save(TransactionMapper.toTransaction(transactionDto));

        accountService.increaseBalance(transaction.getAccountReceiverId(), transactionDto.amountTransferred());

        accountService.decreaseBalance(transaction.getAccountSenderId(), transactionDto.amountTransferred() );

        notificationService.send(createNotification(transactionDto));

        return transaction.getId();
    }

    private void accountNotFoundException(String field) {
        throw new BusinessException(String.format(EXCEPTION_MESSAGE, field));
    }

    private NotificationMessage createNotification(TransactionDto transactionDto) {
        return NotificationMessage.builder()
                .message(String.format(NOTIFICATION_MESSAGE,
                        transactionDto.accountSenderId(),
                        transactionDto.amountTransferred()))
                .accountId(transactionDto.accountReceiverId())
                .build();
    }

    @Override
    public Page<TransactionDto> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable)
                .map(TransactionMapper::toTransactionDto);
    }

    @Override
    public TransactionDto find(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return TransactionMapper.toTransactionDto(transaction);
    }
}
