package com.puxpay.errorhandling;

import com.puxpay.errorhandling.exceptions.BadRequestException;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class BadRequestExceptionHandler
        implements ExceptionHandler<BadRequestException, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, BadRequestException e) {
        ErrorResponse error = new ErrorResponse("400", e.getMessage());
        return HttpResponse.badRequest(error);
    }
}