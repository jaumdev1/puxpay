package com.puxpay.features.wallets.command;

import com.puxpay.domain.entities.UserEntity;
import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.errorhandling.exceptions.BadRequestException;
import com.puxpay.features.wallets.infra.WalletRepository;

import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.transaction.annotation.Transactional;


import java.math.BigDecimal;


@RequestScope
public class CreateWalletCommandHandler {

    private final WalletRepository walletRepository;

    public CreateWalletCommandHandler(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletEntity handle(CreateWalletCommand command) {
        if (walletRepository.existsByUser_Id(command.userId())) {
            throw new BadRequestException("Usuário já possui uma carteira associada");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(command.userId());
        WalletEntity walletEntity = new WalletEntity(
                userEntity,
                BigDecimal.ZERO
        );
        walletRepository.save(walletEntity);

        return walletEntity;
    }
}
