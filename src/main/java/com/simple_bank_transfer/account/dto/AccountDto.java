package com.simple_bank_transfer.account.dto;

import lombok.Builder;

@Builder
public record AccountDto(Long id, String name, Integer balance) {
}

