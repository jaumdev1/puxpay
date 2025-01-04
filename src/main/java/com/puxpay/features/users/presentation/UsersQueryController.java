package com.puxpay.features.users.presentation;

import com.puxpay.domain.entities.UserEntity;
import com.puxpay.features.users.presentation.model.UserResponse;
import com.puxpay.features.users.query.GetUserByIdQuery;
import com.puxpay.features.users.query.GetUserByIdQueryHandler;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.UUID;

@Controller("/users")
@Tag(name = "Users", description = "Operações relacionadas a consulta de usuários")
public class UsersQueryController {

    @Inject
    private GetUserByIdQueryHandler getUserByIdQueryHandler;

    @Get("/{id}")
    @Operation(
            summary = "Consulta um usuário por ID",
            description = "Retorna os detalhes de um usuário com base no ID fornecido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public HttpResponse<UserResponse> getUserById(@PathVariable UUID id) {
        var query = new GetUserByIdQuery(id);
        UserEntity user = getUserByIdQueryHandler.handle(query);
        return HttpResponse.ok(new UserResponse(user));
    }
}
