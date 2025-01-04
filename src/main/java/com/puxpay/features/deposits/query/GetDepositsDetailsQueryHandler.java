package com.puxpay.features.deposits.query;

import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.deposits.infra.DepositRepository;
import com.puxpay.features.transfers.infra.TransferRepository;
import com.puxpay.features.transfers.query.GetTransferDetailsQuery;
import io.micronaut.runtime.http.scope.RequestScope;

@RequestScope
public class GetDepositsDetailsQueryHandler {

    private final DepositRepository depositRepository;

    public GetDepositsDetailsQueryHandler(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public DepositEntity handle(GetDepositsDetailsQuery query) {
        return depositRepository.findById(query.depositId()).orElseThrow(() -> new NotFoundException("Depósito não encontrado."));
    }
}
