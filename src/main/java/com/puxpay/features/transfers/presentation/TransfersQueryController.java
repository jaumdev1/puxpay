package com.puxpay.features.transfers.presentation;

import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.features.transfers.presentation.model.TransferResponse;
import com.puxpay.features.transfers.query.ListTransfersByUserQuery;
import com.puxpay.features.transfers.query.ListTransfersByUserQueryHandler;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller("/transfers")
public class TransfersQueryController {

    @Inject
    private ListTransfersByUserQueryHandler listTransfersByUserQueryHandler;

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
