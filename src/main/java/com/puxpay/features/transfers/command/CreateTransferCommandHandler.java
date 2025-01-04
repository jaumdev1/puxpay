package com.puxpay.features.transfers.command;

import com.puxpay.domain.entities.TransferEntity;
import com.puxpay.domain.entities.WalletEntity;
import com.puxpay.domain.enums.UserType;
import com.puxpay.errorhandling.exceptions.BadRequestException;
import com.puxpay.errorhandling.exceptions.TransferRequestIdConstraintViolationException;
import com.puxpay.features.transfers.infra.TransferRepository;

import com.puxpay.features.wallets.command.CreditWalletCommandHandler;
import com.puxpay.features.wallets.command.DebitWalletCommandHandler;
import com.puxpay.features.wallets.query.GetWalletByUserIdQueryHandler;
import com.puxpay.features.wallets.query.GetWalletByUserIdDetailsQuery;
import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.transaction.annotation.Transactional;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;

@RequestScope
public class CreateTransferCommandHandler {

    private final TransferRepository transferRepository;
    private final CreditWalletCommandHandler creditWallet;
    private final DebitWalletCommandHandler debitWallet;
    private final GetWalletByUserIdQueryHandler getWalletByUserId;


    public CreateTransferCommandHandler(TransferRepository transferRepository,
                                        CreditWalletCommandHandler creditWallet, DebitWalletCommandHandler debitWallet, GetWalletByUserIdQueryHandler getWalletByUserId) {
        this.transferRepository = transferRepository;
        this.creditWallet = creditWallet;
        this.debitWallet = debitWallet;
        this.getWalletByUserId = getWalletByUserId;
    }

    @Transactional
    public TransferEntity handle(CreateTransferCommand cmd) {
        try {

            Optional<TransferEntity> existing = transferRepository.findByRequestId(cmd.requestId());
            if (existing.isPresent())
                return existing.get();

            if (cmd.payerId().equals(cmd.payeeId()))
                throw new BadRequestException("O payer e o payee não podem ser o mesmo usuário");

            var payer = new GetWalletByUserIdDetailsQuery(
                    cmd.payerId()
            );
            var payee = new GetWalletByUserIdDetailsQuery(
                    cmd.payeeId()
            );
            WalletEntity payerWallet = getWalletByUserId.handle(payer);
            WalletEntity payeeWallet = getWalletByUserId.handle(payee);

            if(payeeWallet.getUser().getUserType().equals(UserType.MERCHANT))
                throw new BadRequestException("O Lojista não pode efetuar transferências.");


            debitWallet.handle(cmd.payerId(), cmd.value());

            creditWallet.handle(cmd.payeeId(), cmd.value());

            TransferEntity transfer = new TransferEntity(cmd.requestId(),payerWallet, payeeWallet, cmd.value());

            transferRepository.save(transfer);

            return transfer;
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new TransferRequestIdConstraintViolationException(
                        "Transferência duplicada, mas não foi possível recuperar."
                );
            }
            throw e;
        }
    }
}
