package com.puxpay.features.wallets.query;

import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.wallets.infra.WalletRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

@Singleton
public class GetWalletByUserIdQueryHandler {

    private final WalletRepository walletRepository;

    public GetWalletByUserIdQueryHandler(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(readOnly = true)
    public WalletEntity handle(GetWalletByUserIdDetailsQuery query) {
        return walletRepository.findByUserId(query.userId())
                .orElseThrow(() -> new NotFoundException("Wallet não encontrada para o usuário " + query.userId()));
    }
}
