package com.puxpay.features.transfers.query;


import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.features.transfers.infra.TransferRepository;
import io.micronaut.runtime.http.scope.RequestScope;
import jakarta.inject.Singleton;

import java.util.List;

@RequestScope
public class ListTransfersByUserQueryHandler {

    private final TransferRepository transferRepository;

    public ListTransfersByUserQueryHandler(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<TransferEntity> handle(ListTransfersByUserQuery query) {
        return transferRepository.findByUserId(query.userId());
    }
}
