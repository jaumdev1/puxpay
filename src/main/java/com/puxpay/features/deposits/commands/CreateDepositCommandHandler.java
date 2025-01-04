package com.puxpay.features.deposits.commands;

import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.features.deposits.infra.DepositRepository;
import com.puxpay.features.wallets.command.CreditWalletCommandHandler;

import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.transaction.annotation.Transactional;


@RequestScope
public class CreateDepositCommandHandler {

    private final DepositRepository depositRepository;
    private final CreditWalletCommandHandler creditWalletCommandHandler;

    public CreateDepositCommandHandler(DepositRepository depositRepository,
                                       CreditWalletCommandHandler creditWalletCommandHandler) {
        this.depositRepository = depositRepository;
        this.creditWalletCommandHandler = creditWalletCommandHandler;
    }

    @Transactional
    public DepositEntity handle(CreateDepositCommand cmd) {

     WalletEntity wallet = creditWalletCommandHandler.handle(cmd.userId(),cmd.amount());

        DepositEntity deposit = new DepositEntity(
                cmd.amount(),
                wallet
        );
        depositRepository.save(deposit);

        return deposit;
    }
}
