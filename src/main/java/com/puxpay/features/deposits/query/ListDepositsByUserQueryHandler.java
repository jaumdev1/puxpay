package com.puxpay.features.deposits.query;

import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.features.deposits.infra.DepositRepository;
import io.micronaut.runtime.http.scope.RequestScope;

import java.util.List;

@RequestScope
public class ListDepositsByUserQueryHandler {

    private final DepositRepository depositRepository;

    public ListDepositsByUserQueryHandler(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public List<DepositEntity> handle(ListDepositsByUserQuery query) {
        return depositRepository.findByUserId(query.userId());
    }
}