package com.puxpay.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets", schema = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Version
    private Long version;

    public WalletEntity(UserEntity user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }
}
