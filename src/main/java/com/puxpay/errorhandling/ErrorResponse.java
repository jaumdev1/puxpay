package com.puxpay.errorhandling;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ErrorResponse {
    private String code;
    private String description;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // getters e setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
