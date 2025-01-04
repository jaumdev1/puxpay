package com.puxpay.features.transfers.presentation.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
@Data
@Introspected
public class TransferRequest {

    @NotNull(message = "O ID do pagador (payerId) é obrigatório")
    private UUID payerId;

    @NotNull(message = "O ID do recebedor (payeeId) é obrigatório")
    private UUID payeeId;

    @NotNull(message = "O valor da transferência é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "O valor da transferência deve ser maior que zero")
    private BigDecimal value;

    @NotNull(message = "O requestId é obrigatório")
    private UUID requestId;
}
