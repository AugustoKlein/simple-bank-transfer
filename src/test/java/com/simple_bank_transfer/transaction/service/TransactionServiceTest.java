package com.simple_bank_transfer.transaction.service;

import com.simple_bank_transfer.account.service.AccountService;
import com.simple_bank_transfer.account.stubs.AccountStub;
import com.simple_bank_transfer.infra.exception.AccountReceiverNotFoundException;
import com.simple_bank_transfer.infra.exception.AccountSenderNotFoundException;
import com.simple_bank_transfer.notification.service.NotificationService;
import com.simple_bank_transfer.transaction.dto.TransactionDto;
import com.simple_bank_transfer.transaction.repository.TransactionRepository;
import com.simple_bank_transfer.transaction.service.impl.TransactionServiceImpl;
import com.simple_bank_transfer.transaction.stubs.TransactionStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Game Service Test")
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    public static final String DEFAULT_ERROR_MESSAGE = "something went wrong";

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AccountService accountService;

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("Should create transaction, manage balance from accounts and send notification message")
        void createSuccess() {
            when(accountService.exists(anyLong())).thenReturn(true);
            when(transactionRepository.save(any())).thenReturn(TransactionStub.transaction());

            doNothing().when(accountService).increaseBalance(anyLong(), anyLong());
            doNothing().when(accountService).decreaseBalance(anyLong(), anyLong());

            when(notificationService.create(any())).thenReturn(1L);

            Long id = transactionService.create(TransactionStub.transactionDto());

            assertEquals(id, TransactionStub.transaction().getId());

            verify(accountService, times(2)).exists(anyLong());
            verify(transactionRepository).save(any());
            verify(accountService).increaseBalance(anyLong(), anyLong());
            verify(accountService).decreaseBalance(anyLong(), anyLong());
            verify(notificationService).create(any());
        }

        @Test
        @DisplayName("Should throw AccountReceiverNotFoundException when account does not exist")
        void createAccountReceiverNotFoundException() {
            TransactionDto transactionDto = TransactionStub.transactionDto();

            when(accountService.exists(transactionDto.accountReceiverId())).thenReturn(false);

            assertThrows(AccountReceiverNotFoundException.class, () -> transactionService.create(TransactionStub.transactionDto()));

            verify(accountService, times(1)).exists(transactionDto.accountReceiverId());
            verify(transactionRepository, times(0)).save(any());
            verify(accountService, times(0)).increaseBalance(anyLong(), anyLong());
            verify(accountService, times(0)).decreaseBalance(anyLong(), anyLong());
            verify(notificationService, times(0)).create(any());
        }

        @Test
        @DisplayName("Should throw AccountSenderNotFoundException when account does not exist")
        void createAccountSenderNotFoundException() {
            TransactionDto transactionDto = TransactionStub.transactionDto();

            when(accountService.exists(transactionDto.accountReceiverId())).thenReturn(true);

            when(accountService.exists(transactionDto.accountSenderId())).thenReturn(false);

            assertThrows(AccountSenderNotFoundException.class, () -> transactionService.create(transactionDto));

            verify(accountService, times(1)).exists(transactionDto.accountReceiverId());
            verify(accountService, times(1)).exists(transactionDto.accountSenderId());
            verify(transactionRepository, times(0)).save(any());
            verify(accountService, times(0)).increaseBalance(anyLong(), anyLong());
            verify(accountService, times(0)).decreaseBalance(anyLong(), anyLong());
            verify(notificationService, times(0)).create(any());
        }




    }

}
