package com.puxpay.features.deposits.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateDepositCommand(UUID userId, BigDecimal amount) {
}
