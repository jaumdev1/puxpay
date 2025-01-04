package com.puxpay.errorhandling;

import com.puxpay.errorhandling.exceptions.NotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundException, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, NotFoundException e) {
        ErrorResponse error = new ErrorResponse(
                "500",
                (e.getMessage() != null && !e.getMessage().isBlank())
                        ? e.getMessage()
                        : "Erro interno no servidor"
        );
        return HttpResponse.serverError(error);
    }
}
