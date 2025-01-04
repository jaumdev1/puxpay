package com.puxpay.features.deposits.presentation.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
@AllArgsConstructor
@Data
@Introspected
public class DepositRequest {

    @NotNull(message = "O ID do usuário (userId) é obrigatório")
    private UUID userId;

    @NotNull(message = "O valor do depósito (amount) é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "O valor do depósito deve ser maior que zero")
    private BigDecimal amount;
}
