package com.puxpay.features.wallets.query;

import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.wallets.infra.WalletRepository;
import io.micronaut.transaction.annotation.Transactional;

import java.util.UUID;

public class GetWalletByIdQueryHandler {

    private final WalletRepository walletRepository;

    public GetWalletByIdQueryHandler(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    @Transactional(readOnly = true)
    public WalletEntity handle(UUID id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Wallet não encontrada"));
    }
}
