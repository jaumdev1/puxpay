package com.puxpay.features.deposits.presentation;

import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.features.deposits.commands.CreateDepositCommand;
import com.puxpay.features.deposits.commands.CreateDepositCommandHandler;
import com.puxpay.features.deposits.presentation.model.DepositRequest;
import com.puxpay.features.deposits.presentation.model.DepositResponse;
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

@Controller("/deposits")
@Tag(name = "Deposits", description = "Operações relacionadas a depósitos")
public class DepositsCommandController {

    @Inject
    private CreateDepositCommandHandler createDepositHandler;

    @Post
    @Operation(
            summary = "Deposita valor para um usuário",
            description = "Endpoint para realizar um depósito na carteira de um usuário.",
            requestBody = @RequestBody(
                    description = "Detalhes do depósito a ser realizado",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = DepositRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Depósito realizado com sucesso",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = DepositResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public HttpResponse<DepositResponse> deposit(@Valid @Body DepositRequest request) {
        var cmd = new CreateDepositCommand(request.getUserId(), request.getAmount());
        DepositEntity deposit = createDepositHandler.handle(cmd);

        DepositResponse response = new DepositResponse(
                deposit.getId(),
                deposit.getWallet().getUser().getId(),
                deposit.getAmount()
        );
        return HttpResponse.created(response);
    }
}
