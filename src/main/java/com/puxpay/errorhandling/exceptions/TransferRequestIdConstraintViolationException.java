package com.puxpay.errorhandling.exceptions;

public class TransferRequestIdConstraintViolationException extends RuntimeException {
    public TransferRequestIdConstraintViolationException(String message) {
        super(message);
    }
}
