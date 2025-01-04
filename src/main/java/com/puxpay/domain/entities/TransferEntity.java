package com.puxpay.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transfers", schema = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class TransferEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_wallet_id")
    private WalletEntity fromWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_wallet_id")
    private WalletEntity toWallet;

    private BigDecimal value;

    @Column(nullable = false, unique = true)
    private UUID requestId;

    public TransferEntity(UUID requestId, WalletEntity fromWallet, WalletEntity toWallet, BigDecimal value) {
        this.requestId = requestId;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.value = value;
    }
}
