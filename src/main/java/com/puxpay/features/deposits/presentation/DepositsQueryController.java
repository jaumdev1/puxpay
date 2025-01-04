package com.puxpay.features.deposits.presentation;

import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.domain.entities.UserEntity;
import com.puxpay.features.deposits.presentation.model.DepositResponse;
import com.puxpay.features.deposits.query.GetDepositsDetailsQuery;
import com.puxpay.features.deposits.query.GetDepositsDetailsQueryHandler;
import com.puxpay.features.deposits.query.ListDepositsByUserQuery;
import com.puxpay.features.deposits.query.ListDepositsByUserQueryHandler;
import com.puxpay.features.users.presentation.model.UserResponse;
import com.puxpay.features.users.query.GetUserByIdQuery;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller("/deposits")
public class DepositsQueryController {

    @Inject
    private ListDepositsByUserQueryHandler listDepositsByUserQueryHandler;
    @Inject
    private GetDepositsDetailsQueryHandler getDepositsDetailsQueryHandler;

    @Get("/by-user/{userId}")
    public List<DepositResponse> listByUser(@PathVariable UUID userId) {
        var query = new ListDepositsByUserQuery(userId);
        List<DepositEntity> deposits = listDepositsByUserQueryHandler.handle(query);
        return deposits.stream()
                .map(deposit -> new DepositResponse(
                        deposit.getId(),
                        deposit.getWallet().getUser().getId(),
                        deposit.getAmount()
                ))
                .collect(Collectors.toList());
    }
    @Get("/{id}")
    public HttpResponse<DepositResponse> getUserById(@PathVariable UUID id) {
        var query = new GetDepositsDetailsQuery(id);
        DepositEntity deposit = getDepositsDetailsQueryHandler.handle(query);
        return HttpResponse.ok(new DepositResponse(deposit));
    }
}