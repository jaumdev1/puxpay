package com.puxpay.features.users.presentation;


import com.puxpay.domain.entities.UserEntity;
import com.puxpay.domain.enums.UserType;
import com.puxpay.features.users.command.CreateUserCommand;
import com.puxpay.features.users.command.CreateUserCommandHandler;
import com.puxpay.features.users.presentation.model.CreateUserRequest;
import com.puxpay.features.users.presentation.model.UserResponse;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/users")
public class UsersCommandController {

    @Inject
    private CreateUserCommandHandler createUserHandler;

    @Post
    @Operation(summary = "Cria um novo usuário", description = "Endpoint para inserir um usuário no sistema")
    public HttpResponse<UserResponse> create(@Valid @Body CreateUserRequest request) {
        CreateUserCommand cmd = new CreateUserCommand(
                request.getName(),
                request.getDocument(),
                request.getEmail(),
                request.getPassword(),
                request.isMerchant() ? UserType.MERCHANT : UserType.REGULAR
        );

        UserEntity user = createUserHandler.handle(cmd);

        return HttpResponse.created(new UserResponse(user));
    }
}
