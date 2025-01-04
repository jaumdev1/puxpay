package com.puxpay.errorhandling;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.OptimisticLockException;
import io.micronaut.http.server.exceptions.ExceptionHandler;
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class OptimisticLockExceptionHandler
        implements ExceptionHandler<OptimisticLockException, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, OptimisticLockException e) {
        ErrorResponse error = new ErrorResponse(
                "409",
                "Conflito de concorrência: o registro foi alterado por outro processo."
        );
        return HttpResponse.status(HttpStatus.CONFLICT).body(error);
    }
}
