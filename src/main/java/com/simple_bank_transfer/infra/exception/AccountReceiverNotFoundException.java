package com.simple_bank_transfer.infra.exception;

public class AccountReceiverNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Receiver account number does not exist";

    public AccountReceiverNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
