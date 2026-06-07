package com.simple_bank_transfer.account.service;

import com.simple_bank_transfer.account.dto.AccountDto;
import com.simple_bank_transfer.account.repository.AccountRepository;
import com.simple_bank_transfer.account.repository.entity.Account;
import com.simple_bank_transfer.account.service.impl.AccountServiceImpl;
import com.simple_bank_transfer.account.stubs.AccountStub;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Account Service Test")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    public static final String DEFAULT_ERROR_MESSAGE = "something went wrong";

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Nested
    @DisplayName("Create")
    class Create {

        @Test
        @DisplayName("Should create account")
        public void createSuccess() {
            when(accountRepository.save(any())).thenReturn(AccountStub.account());

            Long id = accountService.create(AccountStub.accountDto());

            assertEquals(1L, id);
            verify(accountRepository).save(any());
        }

        @Test
        @DisplayName("Should not create when IllegalArgumentException is thrown")
        public void createFail() {
            when(accountRepository.save(any())).thenThrow(new IllegalArgumentException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(IllegalArgumentException.class, () -> accountService.create(AccountStub.accountDto())).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(accountRepository).save(any());
        }
    }

    @Nested
    @DisplayName("Find")
    class Find {
        @Test
        @DisplayName("Should find account by id")
        public void findSuccess() {
            when(accountRepository.findById(anyLong())).thenReturn(Optional.of(AccountStub.account()));

            Long id = 1L;
            AccountDto accountDto = accountService.find(id);

            assertEquals(id, accountDto.id());

            verify(accountRepository).findById(anyLong());
        }

        @Test
        @DisplayName("Should not find account when EntityNotFoundException is thrown")
        public void findFail() {
            when(accountRepository.findById(any())).thenThrow(new EntityNotFoundException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(EntityNotFoundException.class, () -> accountService.find(1L)).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(accountRepository).findById(any());
        }
    }

    @Nested
    @DisplayName("IncreaseBalance")
    class IncreaseBalance {
        @Test
        @DisplayName("Should increase balance from account")
        public void increaseBalanceSuccess() {
            when(accountRepository.findById(anyLong())).thenReturn(Optional.of(AccountStub.account()));
            when(accountRepository.save(any())).thenReturn(AccountStub.account());

            Long id = 1L;
            Long amount = 10L;
            accountService.increaseBalance(id, amount);

            verify(accountRepository).findById(anyLong());
            verify(accountRepository).save(any());
        }

        @Test
        @DisplayName("Should not increase balance from account when EntityNotFoundException is thrown")
        public void increaseBalanceEntityNotFoundException() {
            when(accountRepository.findById(any())).thenThrow(new EntityNotFoundException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(EntityNotFoundException.class, () -> accountService.increaseBalance(1L, 10L)).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(accountRepository).findById(any());
            verify(accountRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should not increase balance when IllegalArgumentException is thrown")
        public void increaseBalanceIllegalArgumentException() {
            when(accountRepository.findById(anyLong())).thenReturn(Optional.of(AccountStub.account()));
            when(accountRepository.save(any())).thenThrow(new IllegalArgumentException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(IllegalArgumentException.class, () -> accountService.increaseBalance(1L, 10L)).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(accountRepository).findById(any());
            verify(accountRepository).save(any());
        }
    }

    @Nested
    @DisplayName("DecreaseBalance")
    class DecreaseBalance {
        @Test
        @DisplayName("Should decrease balance from account")
        public void decreaseBalanceSuccess() {
            when(accountRepository.findById(anyLong())).thenReturn(Optional.of(AccountStub.account()));
            when(accountRepository.save(any())).thenReturn(AccountStub.account());

            Long id = 1L;
            Long amount = 10L;
            accountService.decreaseBalance(id, amount);

            verify(accountRepository).findById(anyLong());
            verify(accountRepository).save(any());
        }

        @Test
        @DisplayName("Should not decrease balance from account when EntityNotFoundException is thrown")
        public void decreaseBalanceEntityNotFoundException() {
            when(accountRepository.findById(any())).thenThrow(new EntityNotFoundException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(EntityNotFoundException.class, () -> accountService.decreaseBalance(1L, 10L)).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(accountRepository).findById(any());
            verify(accountRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should not decrease balance when IllegalArgumentException is thrown")
        public void decreaseBalanceIllegalArgumentException() {
            when(accountRepository.findById(anyLong())).thenReturn(Optional.of(AccountStub.account()));
            when(accountRepository.save(any())).thenThrow(new IllegalArgumentException(DEFAULT_ERROR_MESSAGE));

            String message = assertThrows(IllegalArgumentException.class, () -> accountService.decreaseBalance(1L, 10L)).getMessage();

            assertEquals(DEFAULT_ERROR_MESSAGE, message);
            verify(accountRepository).findById(any());
            verify(accountRepository).save(any());
        }
    }
    
    @Nested
    @DisplayName("Exists")
    class Exists {
        @Test
        @DisplayName("Should return a boolean value")
        void exists() {
            when(accountRepository.existsById(anyLong())).thenReturn(anyBoolean());

           Boolean exists = accountService.exists(1L);

            assertNotNull(exists);
            verify(accountRepository).existsById(anyLong());
        }
    }

    @Nested
    @DisplayName("FindAll")
    class FindAll {
        @Test
        @DisplayName("Should find all accounts by pageable")
        void findAllSuccess() {
            Pageable pageable = PageRequest.of(0, 10);

            Page<Account> expectedPage = new PageImpl<>(List.of(AccountStub.account()), pageable, 1);

            when(accountRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

            Long id = 1L;
            Page<AccountDto> accountDtoPage = accountService.findAll(pageable);

            assertNotNull(accountDtoPage);

            verify(accountRepository).findAll(any(Pageable.class));
        }
    }


}
