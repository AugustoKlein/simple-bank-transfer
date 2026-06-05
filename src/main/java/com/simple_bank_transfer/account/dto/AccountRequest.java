package com.simple_bank_transfer.account.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record AccountRequest(@NonNull String name, @NonNull Integer balance) {
}
