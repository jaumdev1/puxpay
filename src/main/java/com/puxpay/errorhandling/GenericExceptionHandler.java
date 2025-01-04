package com.puxpay.errorhandling;

import io.micronaut.http.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class GenericExceptionHandler implements ExceptionHandler<Exception, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, Exception e) {
        ErrorResponse error = new ErrorResponse(
                "500",
                (e.getMessage() != null && !e.getMessage().isBlank())
                        ? e.getMessage()
                        : "Erro interno no servidor"
        );
        return HttpResponse.serverError(error);
    }
}
