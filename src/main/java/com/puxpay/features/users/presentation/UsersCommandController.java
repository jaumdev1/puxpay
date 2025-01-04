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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/users")
@Tag(name = "Users", description = "Operações relacionadas a usuários")
public class UsersCommandController {

    @Inject
    private CreateUserCommandHandler createUserHandler;

    @Post
    @Operation(
            summary = "Cria um novo usuário",
            description = "Endpoint para criar um novo usuário no sistema.",
            requestBody = @RequestBody(
                    description = "Detalhes do usuário a ser criado",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = CreateUserRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
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
