package com.puxpay.errorhandling;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Order;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Requires(classes = ConstraintViolationException.class)
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<ValidationErrorResponse>> {

    @Override
    public HttpResponse<ValidationErrorResponse> handle(HttpRequest request, ConstraintViolationException exception) {
        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.getCode(),
                "Bad Request",
                messages
        );

        return HttpResponse.badRequest(response);
    }
}
