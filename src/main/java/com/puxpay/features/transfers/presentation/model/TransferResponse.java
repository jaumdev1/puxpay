package com.puxpay.features.transfers.presentation.model;

import com.puxpay.domain.entities.TransferEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
@Data
public class TransferResponse {
    private UUID transferId;
    private UUID payerId;
    private UUID payeeId;
    private BigDecimal value;


    public TransferResponse(UUID transferId, UUID payerId, UUID payeeId, BigDecimal value) {
        this.transferId = transferId;
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.value = value;
    }

    public TransferResponse(TransferEntity transferEntity) {
        this.transferId = transferEntity.getId();
        this.payerId = transferEntity.getFromWallet().getUser().getId();
        this.payeeId = transferEntity.getToWallet().getUser().getId();
        this.value = transferEntity.getValue();
    }
}