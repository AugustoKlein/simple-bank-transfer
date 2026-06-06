package com.simple_bank_transfer.transaction.controller;

import com.simple_bank_transfer.transaction.stubs.TransactionStub;
import com.simple_bank_transfer.transaction.service.TransactionService;
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

@DisplayName("Transaction Controller Test")
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;


    @Nested
    class Create {
        @Test
        @DisplayName("Should create transaction and return 201")
        public void createSuccess() throws Exception {
            when(transactionService.create(any())).thenReturn(1L);

            mockMvc.perform(post("/transaction")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TransactionStub.transactionDto())))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/transaction/1"));
        }

        @Test
        @DisplayName("Should not create transaction and return 500")
        public void createFail() throws Exception {
            when(transactionService.create(any())).thenThrow(new IllegalArgumentException());

            mockMvc.perform(post("/transaction")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TransactionStub.transactionDto())))
                    .andExpect(status().isInternalServerError())
                    .andExpect(header().doesNotExist("Location"));
        }
    }

    @Nested
    class Find {
        @Test
        @DisplayName("Should find transaction and return 200")
        public void findSuccess() throws Exception {
            when(transactionService.find(any())).thenReturn(TransactionStub.transactionDto());

            mockMvc.perform(get("/transaction/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TransactionStub.transactionDto())))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 500 when exception is thrown")
        public void findFail() throws Exception {
            when(transactionService.find(any())).thenThrow(new IllegalArgumentException());

            mockMvc.perform(get("/transaction/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TransactionStub.transactionDto())))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class FindAll {
        @Test
        @DisplayName("Should find all transactions and return 200")
        public void findAllSuccess() throws Exception {
            when(transactionService.findAll(any())).thenReturn(new PageImpl<>(List.of(TransactionStub.transactionDto())));

            mockMvc.perform(get("/transaction")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TransactionStub.transactionDto())))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 500 when exception is thrown")
        public void findAllFail() throws Exception {
            when(transactionService.findAll(any())).thenThrow(new IllegalArgumentException());

            mockMvc.perform(get("/transaction")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TransactionStub.transactionDto())))
                    .andExpect(status().isInternalServerError());
        }
    }
    
}
