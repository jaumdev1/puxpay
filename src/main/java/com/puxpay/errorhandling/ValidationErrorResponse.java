package com.puxpay.errorhandling;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

import java.util.List;

@Serdeable
@Getter
public class ValidationErrorResponse {
    private int status;
    private String message;
    private List<String> errors;

    public ValidationErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

}
