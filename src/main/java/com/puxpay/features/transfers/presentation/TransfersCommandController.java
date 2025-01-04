package com.puxpay.features.transfers.presentation;

import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.features.transfers.command.CreateTransferCommand;
import com.puxpay.features.transfers.command.CreateTransferCommandHandler;
import com.puxpay.features.transfers.presentation.model.TransferRequest;
import com.puxpay.features.transfers.presentation.model.TransferResponse;
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

@Tag(name = "Transfers", description = "Gerenciamento de transferências")
@Controller("/transfers")
public class TransfersCommandController {

    @Inject
    private CreateTransferCommandHandler createTransferHandler;

    @Operation(
            summary = "Cria uma nova transferência",
            description = "Movimenta saldo de payer para payee",
            requestBody = @RequestBody(
                    description = "Detalhes da solicitação de transferência",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransferRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Transferência criada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransferResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos na solicitação",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @Post
    public HttpResponse<TransferResponse> create(@Valid @Body TransferRequest request) {
        var cmd = new CreateTransferCommand(
                request.getRequestId(),
                request.getPayerId(),
                request.getPayeeId(),
                request.getValue()
        );

        TransferEntity transfer = createTransferHandler.handle(cmd);

        TransferResponse response = new TransferResponse(
                transfer.getId(),
                transfer.getFromWallet().getUser().getId(),
                transfer.getToWallet().getUser().getId(),
                transfer.getValue()
        );
        return HttpResponse.created(response);
    }
}

