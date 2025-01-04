package com.puxpay.features.transfers.query;


import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.transfers.infra.TransferRepository;
import io.micronaut.runtime.http.scope.RequestScope;
import jakarta.inject.Singleton;

import java.util.Optional;

@RequestScope
public class GetTransferDetailsQueryHandler {

    private final TransferRepository transferRepository;

    public GetTransferDetailsQueryHandler(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public TransferEntity handle(GetTransferDetailsQuery query) {
        return transferRepository.findById(query.transferId()).orElseThrow(() -> new NotFoundException("Transferência não encontrado."));
    }
}