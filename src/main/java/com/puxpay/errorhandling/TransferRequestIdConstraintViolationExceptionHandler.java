package com.puxpay.errorhandling;


import com.puxpay.errorhandling.exceptions.TransferRequestIdConstraintViolationException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Requires(classes = TransferRequestIdConstraintViolationException.class)
public class TransferRequestIdConstraintViolationExceptionHandler implements ExceptionHandler<TransferRequestIdConstraintViolationException, HttpResponse<ErrorResponse>> {

@Override
public HttpResponse<ErrorResponse> handle(HttpRequest request, TransferRequestIdConstraintViolationException exception) {
    ErrorResponse error = new ErrorResponse(
            "409",
            "Operação duplicada. Essa transferência já foi processada."
    );
    return HttpResponse.status(HttpStatus.CONFLICT).body(error);
}
}