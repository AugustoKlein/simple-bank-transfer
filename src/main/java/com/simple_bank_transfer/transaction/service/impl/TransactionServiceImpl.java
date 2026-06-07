package com.simple_bank_transfer.transaction.service.impl;

import com.simple_bank_transfer.account.service.AccountService;
import com.simple_bank_transfer.infra.exception.AccountReceiverNotFoundException;
import com.simple_bank_transfer.infra.exception.AccountSenderNotFoundException;
import com.simple_bank_transfer.notification.dto.NotificationDto;
import com.simple_bank_transfer.notification.enums.StatusEnum;
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

    private TransactionRepository transactionRepository;

    private NotificationService notificationService;

    private AccountService accountService;

    @Transactional
    @Override
    public Long create(TransactionDto transactionDto) {
        log.info("Create transaction");

        if (!accountService.exists(transactionDto.accountReceiverId())) {
            throw new AccountReceiverNotFoundException();
        }

        if (!accountService.exists(transactionDto.accountSenderId())) {
            throw new AccountSenderNotFoundException();
        }

        Transaction transaction = transactionRepository.save(TransactionMapper.toTransaction(transactionDto));

        accountService.increaseBalance(transaction.getAccountReceiverId(), transactionDto.amountTransferred());

        accountService.decreaseBalance(transaction.getAccountSenderId(), transactionDto.amountTransferred());

        notificationService.create(createNotification(transactionDto));

        return transaction.getId();
    }

    private NotificationDto createNotification(TransactionDto transactionDto) {
        return NotificationDto.builder()
                .message(String.format(NOTIFICATION_MESSAGE,
                        transactionDto.accountSenderId(),
                        transactionDto.amountTransferred()))
                .accountId(transactionDto.accountReceiverId())
                .status(StatusEnum.PENDING)
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
