package com.puxpay.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deposits", schema = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositEntity extends BaseEntity {

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet;
}
