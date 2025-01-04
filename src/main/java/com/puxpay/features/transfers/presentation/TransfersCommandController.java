package com.puxpay.features.transfers.presentation;

import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.features.transfers.command.CreateTransferCommand;
import com.puxpay.features.transfers.command.CreateTransferCommandHandler;
import com.puxpay.features.transfers.presentation.model.TransferRequest;
import com.puxpay.features.transfers.presentation.model.TransferResponse;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/transfers")
public class TransfersCommandController {

    @Inject
    private CreateTransferCommandHandler createTransferHandler;

    @Operation(summary = "Cria uma nova transferência", description = "Movimenta saldo de payer para payee")
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