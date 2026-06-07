package com.simple_bank_transfer.infra.exception;

public class AccountSenderNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Sender account number does not exist";

    public AccountSenderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
