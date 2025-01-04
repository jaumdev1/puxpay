package com.puxpay.features.users.presentation.model;

import com.puxpay.domain.entities.UserEntity;
import com.puxpay.domain.enums.UserType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Serdeable
@Data
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String document;
    private String email;
    private UserType userType;
    private BigDecimal balance;

    public UserResponse(UserEntity entity) {
        this.id = entity.getId() != null ? entity.getId().toString() : null;
        this.name = entity.getName();
        this.document = entity.getDocument();
        this.email = entity.getEmail();
        this.userType = entity.getUserType();
        this.balance = entity.getWallet().getBalance();
    }
}
