package com.puxpay.features.transfers.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransferCommand(UUID requestId, UUID payerId, UUID payeeId, BigDecimal value) {

}