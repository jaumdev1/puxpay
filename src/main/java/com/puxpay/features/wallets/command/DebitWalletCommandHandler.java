package com.puxpay.features.wallets.command;

import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.errorhandling.exceptions.BadRequestException;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.wallets.infra.WalletRepository;
import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequestScope
public class DebitWalletCommandHandler {
    private final WalletRepository walletRepository;

    public DebitWalletCommandHandler(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    @Transactional
    public void handle(UUID userId, BigDecimal amount) {
        WalletEntity wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Wallet não encontrada para o usuário " + userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Saldo insuficiente na wallet do usuário " + userId);
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.update(wallet);
    }
}
