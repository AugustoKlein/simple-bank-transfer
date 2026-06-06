package com.simple_bank_transfer.account.controller;

import com.simple_bank_transfer.account.service.AccountService;
import com.simple_bank_transfer.account.stubs.AccountStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Account Controller Test")
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class Create {
        @Test
        @DisplayName("Should create account and return 201")
        public void createSuccess() throws Exception {
            when(accountService.create(any())).thenReturn(1L);

            mockMvc.perform(post("/account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(AccountStub.accountDto())))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/account/1"));
        }

        @Test
        @DisplayName("Should not create account and return 500")
        public void createFail() throws Exception {
            when(accountService.create(any())).thenThrow(new IllegalArgumentException());

            mockMvc.perform(post("/account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(AccountStub.accountDto())))
                    .andExpect(status().isInternalServerError())
                    .andExpect(header().doesNotExist("Location"));
        }
    }

    @Nested
    class Find {
        @Test
        @DisplayName("Should find account and return 200")
        public void findSuccess() throws Exception {
            when(accountService.find(any())).thenReturn(AccountStub.accountDto());

            mockMvc.perform(get("/account/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(AccountStub.accountDto())))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 500 when exception is thrown")
        public void findFail() throws Exception {
            when(accountService.find(any())).thenThrow(new IllegalArgumentException());

            mockMvc.perform(get("/account/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(AccountStub.accountDto())))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class FindAll {
        @Test
        @DisplayName("Should find all accounts and return 200")
        public void findAllSuccess() throws Exception {
            when(accountService.findAll(any())).thenReturn(new PageImpl<>(List.of(AccountStub.accountDto())));

            mockMvc.perform(get("/account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(AccountStub.accountDto())))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 500 when exception is thrown")
        public void findAllFail() throws Exception {
            when(accountService.findAll(any())).thenThrow(new IllegalArgumentException());

            mockMvc.perform(get("/account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(AccountStub.accountDto())))
                    .andExpect(status().isInternalServerError());
        }
    }
}
