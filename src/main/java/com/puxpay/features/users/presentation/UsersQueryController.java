package com.puxpay.features.users.presentation;

import com.puxpay.domain.entities.UserEntity;
import com.puxpay.features.users.presentation.model.UserResponse;
import com.puxpay.features.users.query.GetUserByIdQuery;
import com.puxpay.features.users.query.GetUserByIdQueryHandler;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@Controller("/users")
public class UsersQueryController {

    @Inject
    private GetUserByIdQueryHandler getUserByIdQueryHandler;

    @Get("/{id}")
    public HttpResponse<UserResponse> getUserById(@PathVariable UUID id) {
        var query = new GetUserByIdQuery(id);
        UserEntity user = getUserByIdQueryHandler.handle(query);
        return HttpResponse.ok(new UserResponse(user));
    }
}
