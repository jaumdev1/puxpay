package com.puxpay.features.deposits.presentation;

import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.features.deposits.presentation.model.DepositResponse;
import com.puxpay.features.deposits.query.GetDepositsDetailsQuery;
import com.puxpay.features.deposits.query.GetDepositsDetailsQueryHandler;
import com.puxpay.features.deposits.query.ListDepositsByUserQuery;
import com.puxpay.features.deposits.query.ListDepositsByUserQueryHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller("/deposits")
@Tag(name = "Deposits", description = "Operações relacionadas a consultas de depósitos")
public class DepositsQueryController {

    @Inject
    private ListDepositsByUserQueryHandler listDepositsByUserQueryHandler;

    @Inject
    private GetDepositsDetailsQueryHandler getDepositsDetailsQueryHandler;

    @Get("/by-user/{userId}")
    @Operation(
            summary = "Lista depósitos por usuário",
            description = "Retorna uma lista de depósitos realizados na carteira de um usuário.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de depósitos retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DepositResponse.class)
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
    public List<DepositResponse> listByUser(@PathVariable UUID userId) {
        var query = new ListDepositsByUserQuery(userId);
        List<DepositEntity> deposits = listDepositsByUserQueryHandler.handle(query);
        return deposits.stream()
                .map(deposit -> new DepositResponse(
                        deposit.getId(),
                        deposit.getWallet().getUser().getId(),
                        deposit.getAmount()
                ))
                .collect(Collectors.toList());
    }

    @Get("/{id}")
    @Operation(
            summary = "Consulta detalhes de um depósito",
            description = "Retorna os detalhes de um depósito específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalhes do depósito retornados com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DepositResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Depósito não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public HttpResponse<DepositResponse> getUserById(@PathVariable UUID id) {
        var query = new GetDepositsDetailsQuery(id);
        DepositEntity deposit = getDepositsDetailsQueryHandler.handle(query);
        return HttpResponse.ok(new DepositResponse(deposit));
    }
}
