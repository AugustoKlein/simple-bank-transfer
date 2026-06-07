package com.simple_bank_transfer.infra.exception;

public class InsufficientBalanceToTransfer extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Not enough balance to transfer this amount";

    public InsufficientBalanceToTransfer() {
        super(DEFAULT_MESSAGE);
    }
}
