package com.puxpay.features.wallets.command;

import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.wallets.infra.WalletRepository;
import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequestScope
public class CreditWalletCommandHandler {
    private final WalletRepository walletRepository;
    public CreditWalletCommandHandler(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletEntity handle(UUID userId, BigDecimal amount) {
        WalletEntity wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Wallet não encontrada para o usuário " + userId));

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.update(wallet);
        return wallet;
    }
}
