package com.simple_bank_transfer.infra.handler;

import com.simple_bank_transfer.infra.dto.ErrorDto;
import com.simple_bank_transfer.infra.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorDto handleEntityNotFoundException(EntityNotFoundException exception) {
        return ErrorDto.builder()
                .message("Entity was not found")
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto handleIllegalArgumentException(IllegalArgumentException exception) {
        return ErrorDto.builder()
                .message("Internal server error")
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorDto handleBusinessException(BusinessException exception) {
        return ErrorDto.builder()
                .message(exception.getMessage())
                .build();
    }
}