package com.puxpay.features.transfers.presentation;

import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.features.transfers.presentation.model.TransferResponse;
import com.puxpay.features.transfers.query.ListTransfersByUserQuery;
import com.puxpay.features.transfers.query.ListTransfersByUserQueryHandler;
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

@Tag(name = "Transfers", description = "Gerenciamento de transferências")
@Controller("/transfers")
public class TransfersQueryController {

    @Inject
    private ListTransfersByUserQueryHandler listTransfersByUserQueryHandler;

    @Operation(
            summary = "Lista transferências por usuário",
            description = "Retorna todas as transferências associadas a um usuário específico",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "userId",
                            description = "ID do usuário cujas transferências serão listadas",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de transferências retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransferResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @Get("/by-user/{userId}")
    public List<TransferResponse> listByUser(@PathVariable UUID userId) {
        var query = new ListTransfersByUserQuery(userId);
        List<TransferEntity> transfers = listTransfersByUserQueryHandler.handle(query);

        return transfers.stream()
                .map(t -> new TransferResponse(
                        t.getId(),
                        t.getFromWallet().getUser().getId(),
                        t.getToWallet().getUser().getId(),
                        t.getValue()
                ))
                .collect(Collectors.toList());
    }
}

