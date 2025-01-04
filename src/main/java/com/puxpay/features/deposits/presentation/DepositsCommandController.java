package com.puxpay.features.deposits.presentation;


import com.puxpay.domain.entities.DepositEntity;
import com.puxpay.features.deposits.commands.CreateDepositCommand;
import com.puxpay.features.deposits.commands.CreateDepositCommandHandler;
import com.puxpay.features.deposits.presentation.model.DepositRequest;
import com.puxpay.features.deposits.presentation.model.DepositResponse;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/deposits")
public class DepositsCommandController {

    @Inject
    private CreateDepositCommandHandler createDepositHandler;

    @Operation(summary = "Deposita valor para um usuário")
    @Post
    public HttpResponse<DepositResponse> deposit(@Valid @Body DepositRequest request) {
        var cmd = new CreateDepositCommand(request.getUserId(), request.getAmount());
        DepositEntity deposit = createDepositHandler.handle(cmd);

        DepositResponse response = new DepositResponse(
                deposit.getId(),
                deposit.getWallet().getUser().getId(),
                deposit.getAmount()
        );
        return HttpResponse.created(response);
    }
}
