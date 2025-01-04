package com.puxpay.features.deposits.presentation.model;

import com.puxpay.domain.entities.DepositEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
@AllArgsConstructor
@Data
public class DepositResponse {
    private UUID depositId;
    private UUID userId;
    private BigDecimal amount;

    public DepositResponse(DepositEntity depositEntity){
        depositId = depositEntity.getId();
        userId = depositEntity.getWallet().getUser().getId();
        amount = depositEntity.getAmount();
    }

}